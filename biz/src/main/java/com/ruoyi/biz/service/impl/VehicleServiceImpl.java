package com.ruoyi.biz.service.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.biz.domain.PO.Company;
import com.ruoyi.biz.domain.VO.TransportCompanyVO;
import com.ruoyi.biz.hik.service.DeleteCarRemoteService;
import com.ruoyi.biz.hik.service.ModifyCarRemoteService;
import com.ruoyi.biz.hik.service.VehicleAddRemoteService;
import com.ruoyi.biz.service.ITransportCompanyService;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.VehicleMapper;
import com.ruoyi.biz.domain.Vehicle;
import com.ruoyi.biz.service.IVehicleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 车辆Service业务层处理
 * 
 * @author yizhi
 * @date 2023-07-13
 */
@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements IVehicleService 
{
    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ITransportCompanyService iTransportCompanyService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private VehicleAddRemoteService vehicleAddRemoteService;

    @Autowired
    private ModifyCarRemoteService modifyCarRemoteService;

    @Autowired
    private DeleteCarRemoteService deleteCarRemoteService;

    /**
     * 查询车辆
     * 
     * @param vehicleId 车辆主键
     * @return 车辆
     */
    @Override
    public Vehicle selectVehicleByVehicleId(Long vehicleId)
    {
        return vehicleMapper.selectVehicleByVehicleId(vehicleId);
    }

    /**
     * 查询车辆列表
     * 
     * @param vehicle 车辆
     * @return 车辆
     */
    @Override
    public List<Vehicle> selectVehicleList(Vehicle vehicle)
    {
            //用户验证
            SysUser user = SecurityUtils.getLoginUser().getUser();
            SysUser sysUser = userService.selectUserById(user.getUserId());
            // 角色集合
            List<SysRole> sysRoless = roleService.selectAllAuthUserList(sysUser.getUserId());
            if(ObjectUtil.isEmpty(vehicle)){
                vehicle=new Vehicle();
            }
            for (SysRole sysRole : sysRoless) {
                if("subaccount".equals(sysRole.getRoleKey())){
                    vehicle.setUserId(user.getUserId());
                    if("admin".equals(sysRole.getRoleKey()) || "manage".equals(sysRole.getRoleKey())){
                        vehicle.setUserId(null);
                    }
                }
            }
            Map<String, Long> collect = sysRoless.stream().filter(x->
                    !"admin".equals(x.getRoleKey()) && !"manage".equals(x.getRoleKey())
                    ).collect(Collectors.toMap(SysRole::getRoleKey, SysRole::getRoleId));
            if(!collect.isEmpty()){
                vehicle.setCompanyId(sysUser.getCompanyId().toString());
            }
            if(ObjectUtil.isNotEmpty(sysUser)&& sysUser.getCompanyId()!=null){
                vehicle.setCompanyId(sysUser.getCompanyId().toString());
            }
            PageUtils.startPage();
            List<Vehicle> vehicles = vehicleMapper.selectVehicleList(vehicle);
            return vehicles;
    }

    /**
     * 根据车牌号查询车辆
     *
     * @param carNum 车牌号
     * @return 车辆集合
     */
    @Override
    public Vehicle selectVehicleOneBycarNum(String carNum) {
        return vehicleMapper.selectVehicleOneBycarNum(carNum);
    }


    private boolean matchCarNum(String carNum){
        // 对车牌进行正则校验
        Pattern pattern = Pattern.compile("([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z]([A-HJ-NP-Z0-9]{5}|[A-HJ-NP-Z0-9]{4})[A-HJ-NP-Z0-9挂学警港澳使领])");
        Matcher matcher = pattern.matcher(carNum);
        if(matcher.matches()){
            return true;
        }
        return false;
    }
    /**
     * 新增车辆
     * 
     * @param vehicle 车辆
     * @return 结果
     */
    @Override
    @Transactional
    public int insertVehicle(Vehicle vehicle,boolean isImportExcel)
    {
        if(ObjectUtil.isNotEmpty(vehicle)){
            if(vehicle.getCarNum()!=null && !"".equals(vehicle.getCarNum())){
                if(!matchCarNum(vehicle.getCarNum())){
                    throw new ServiceException("车牌号格式错误，车牌号请勿输入点且一次只能输入一个车牌号");
                }
                SysUser user = SecurityUtils.getLoginUser().getUser();
                if(user.getCompanyId()==null){
                    throw new ServiceException("当前用户没有所属企业");
                }
                if(!isImportExcel){
                    if(vehicle.getImgUrl()==null){
                        throw new ServiceException("当前用户没有证件证明");
                    }
                    List<String> split = Arrays.asList(vehicle.getImgUrl().split(","));
                    if(split.size()<2){
                        throw new ServiceException("当前用户提供证件不足");
                    }
                }
                Vehicle vehicle1 = vehicleMapper.selectVehicleOneBycarNum(vehicle.getCarNum());
                //判断车辆企业
                if(ObjectUtil.isNotEmpty(vehicle1)){
                    if(user.getCompanyId().toString().compareTo(vehicle1.getCompanyId()) == 0){
                        BeanUtil.copyProperties(vehicle, vehicle1,getNullPropertyNames(vehicle));
                        return this.updateVehicle(vehicle1);
                    }else{
                        throw new ServiceException("该车辆已存在且不属于所属企业");
                    }
                }else{
                    // 通讯海康进行车辆添加
                    ZonedDateTime startTime =
                            ZonedDateTime.ofInstant(vehicle.getStartTime().toInstant(), ZoneId.systemDefault());
                    ZonedDateTime endTime =
                            ZonedDateTime.ofInstant(vehicle.getEndTime().toInstant(), ZoneId.systemDefault());
                    boolean res = vehicleAddRemoteService.addVehicle(vehicle.getCarNum(), startTime, endTime);
                    if(!res){
                        throw new ServiceException("添加车辆失败，与海康威视通讯失败");
                    }
                    vehicle.setCompanyId(user.getCompanyId().toString());
                    vehicle.setCreateTime(DateUtils.getNowDate());
                    vehicle.setUserId(user.getUserId());
                    vehicle.setCreateBy(user.getUserName());
                    vehicle.setUpdateBy(user.getUserName());
                    vehicle.setUpdateTime(DateUtils.getNowDate());
                    vehicle.setStatus("0");
                    return vehicleMapper.insertVehicle(vehicle);
                }
            }else {
                throw new ServiceException("该车未注册车牌号");
            }
        }
        return 0;
    }

    /**
     * 新增车辆
     *
     * @param vehicle 车辆
     * @return 结果
     */
    @Override
    @Transactional
    public int insertVehicleExcel(Vehicle vehicle,boolean isImportExcel)
    {
        if(ObjectUtil.isNotEmpty(vehicle)){
            if(vehicle.getCarNum()!=null && !"".equals(vehicle.getCarNum())){
                SysUser user = SecurityUtils.getLoginUser().getUser();
                if(user.getCompanyId()==null){
                    log.error("当前用户没有所属企业");
                    return 0;
                }
                if(!isImportExcel){
                    if(vehicle.getImgUrl()==null){
                        log.error("当前用户没有证件证明");
                        return 0;
                    }
                    List<String> split = Arrays.asList(vehicle.getImgUrl().split(","));
                    if(split.size()<2){
                        log.error("当前用户提供证件不足");
                        return 0;
                    }
                }
                Vehicle vehicle1 = vehicleMapper.selectVehicleOneBycarNum(vehicle.getCarNum());
                //判断车辆企业
                if(ObjectUtil.isNotEmpty(vehicle1)){
                    if(user.getCompanyId().toString().compareTo(vehicle1.getCompanyId()) == 0){
                        BeanUtil.copyProperties(vehicle, vehicle1,getNullPropertyNames(vehicle));
                        return this.updateVehicle(vehicle1);
                    }else{
                        log.error("该车辆已存在且不属于所属企业");
                        return 0;
                    }
                }else{
                    // 通讯海康进行车辆添加
                    ZonedDateTime startTime =
                            ZonedDateTime.ofInstant(vehicle.getStartTime().toInstant(), ZoneId.systemDefault());
                    ZonedDateTime endTime =
                            ZonedDateTime.ofInstant(vehicle.getEndTime().toInstant(), ZoneId.systemDefault());
                    boolean res = vehicleAddRemoteService.addVehicle(vehicle.getCarNum(), startTime, endTime);
                    if(!res){
                        log.error("添加车辆失败，与海康威视通讯失败");
                        return 0;
                    }
                    vehicle.setCompanyId(user.getCompanyId().toString());
                    vehicle.setCreateTime(DateUtils.getNowDate());
                    vehicle.setUserId(user.getUserId());
                    vehicle.setCreateBy(user.getUserName());
                    vehicle.setUpdateBy(user.getUserName());
                    vehicle.setUpdateTime(DateUtils.getNowDate());
                    if(StringUtils.isEmpty(vehicle.getStatus())){
                        vehicle.setStatus("0");
                    }
                    return vehicleMapper.insertVehicle(vehicle);
                }
            }else {
                log.error("该车未注册车牌号");
                return 0;
            }
        }
        return 0;
    }

    /**
     * 修改车辆
     * 
     * @param vehicle 车辆
     * @return 结果
     */
    @Override
    public int updateVehicle(Vehicle vehicle)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        vehicle.setUpdateBy(user.getUserName());
        vehicle.setUpdateTime(DateUtils.getNowDate());
        vehicle.setUserId(user.getUserId());
        if(vehicle.getImgUrl()==null){
            throw new ServiceException("当前用户没有证件证明");
        }
        List<String> split = Arrays.asList(vehicle.getImgUrl().split(","));
        if(split.size()<2){
            throw new ServiceException("当前用户提供证件不足");
        }
        // 查询车辆是否已经挂靠其他公司
        Vehicle queryVehicle = vehicleMapper.selectVehicleByVehicleId(vehicle.getVehicleId());
        if(ObjectUtil.isNotEmpty(queryVehicle)){
            if(ObjectUtil.isNotEmpty(queryVehicle.getCompanyId())){
                Date endTime = queryVehicle.getEndTime();
                if(DateUtil.compare(new Date(), endTime)<0){
                    if(ObjectUtil.notEqual(user.getCompanyId(), Long.parseLong(queryVehicle.getCompanyId()))){
                        throw new ServiceException("车辆"+vehicle.getCarNum()+"已经被其他公司绑定，您无法添加");
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(vehicle.getStatus()) && CharSequenceUtil.equals(vehicle.getStatus(),"-1")){
            boolean deleteVehicle = deleteCarRemoteService.deleteVehicle(vehicle.getCarNum());
            if(!deleteVehicle){
                throw new ServiceException("海康通讯失败，删除车辆失败");
            }
        }else {
            // 通知海康威视对车辆时间进行修改
            ZonedDateTime startTime =
                    ZonedDateTime.ofInstant(vehicle.getStartTime().toInstant(), ZoneId.systemDefault());
            ZonedDateTime endTime =
                    ZonedDateTime.ofInstant(vehicle.getEndTime().toInstant(), ZoneId.systemDefault());
            modifyCarRemoteService.modifyVehicle(vehicle.getCarNum(), startTime, endTime);
        }
        return vehicleMapper.updateVehicle(vehicle);
    }

    /**
     * 修改车辆(导入用)
     *
     * @param vehicle 车辆
     * @return 结果
     */
    @Override
    public int updateVehicleExcel(Vehicle vehicle)
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        vehicle.setUpdateBy(user.getUserName());
        vehicle.setUpdateTime(DateUtils.getNowDate());
        if(vehicle.getImgUrl()==null){
            log.error("当前用户没有证件证明");
            return 0;
        }
        List<String> split = Arrays.asList(vehicle.getImgUrl().split(","));
        if(split.size()<2){
            log.error("当前用户提供证件不足");
            return 0;
        }
        if(StringUtils.isNotEmpty(vehicle.getStatus()) && CharSequenceUtil.equals(vehicle.getStatus(),"-1")){
            boolean deleteVehicle = deleteCarRemoteService.deleteVehicle(vehicle.getCarNum());
            if(!deleteVehicle){
                log.error("海康通讯失败，删除车辆失败");
                return 0;
            }
        }else {
            // 通知海康威视对车辆时间进行修改
            ZonedDateTime startTime =
                    ZonedDateTime.ofInstant(vehicle.getStartTime().toInstant(), ZoneId.systemDefault());
            ZonedDateTime endTime =
                    ZonedDateTime.ofInstant(vehicle.getEndTime().toInstant(), ZoneId.systemDefault());
            modifyCarRemoteService.modifyVehicle(vehicle.getCarNum(), startTime, endTime);
        }
        return vehicleMapper.updateVehicle(vehicle);
    }

    /**
     * 批量删除车辆
     * 
     * @param vehicleIds 需要删除的车辆主键
     * @return 结果
     */
    @Override
    public int deleteVehicleByVehicleIds(Long[] vehicleIds) {
        for (Long id:vehicleIds) {
            deleteVehicleByVehicleId(id);
        }
        return 1;
    }

    /**
     * 删除车辆信息
     * 
     * @param vehicleId 车辆主键
     * @return 结果
     */
    @Override
    public int deleteVehicleByVehicleId(Long vehicleId)
    {
        Vehicle vehicle = vehicleMapper.selectVehicleByVehicleId(vehicleId);
        boolean deleteVehicle = deleteCarRemoteService.deleteVehicle(vehicle.getCarNum());
        if(!deleteVehicle){
            throw new ServiceException("海康通讯失败，删除车辆失败");
        }
        return vehicleMapper.deleteVehicleByVehicleId(vehicleId);
    }

    /**
     * 查询运输公司（下拉）
     *
     * @return 运输企业
     */
    @Override
    public List<TransportCompanyVO> selectVehicleCompanyList() {
        Company company = new Company();
        return iTransportCompanyService.selectCompanyList(company);
    }

    @Override
    public String importUser(List<Vehicle> vehicleList, boolean updateSupport) {
        {
            int successNum = 0;
            int failureNum = 0;
            int repeatNum = 0;
            String errorMsg = null;
            boolean firstErr = false;
            List<Vehicle> existList = selectVehicleList(null);
            SysUser user = SecurityUtils.getLoginUser().getUser();
            SysUser sysUser = userService.selectUserById(user.getUserId());
            for (Vehicle vehicle : vehicleList){
                try {
                    Long vehicleId = null;
                    for (Vehicle vehicle1 : existList) {
                        if (vehicle1.getCarNum().equals(vehicle.getCarNum())){
                            vehicleId = vehicle1.getVehicleId();
                            repeatNum++;
                            break;
                        }
                    }
                    if(ObjectUtil.isNotEmpty(sysUser) && sysUser.getCompanyId()!=null){
                        vehicle.setCompanyId(sysUser.getCompanyId().toString());
                    }
                    List<SysDictData> vehicleType = dictTypeService.selectDictDataByType("vehicle_type");
                    List<String> newList = vehicleType.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
                    if(!newList.contains(vehicle.getVehicleType())){
                        failureNum++;
                        continue;
                    }
                    if (vehicleId == null) {
                        //导入新增数据校验统计
                        int insert = insertVehicleExcel(vehicle, true);
                        if(insert>0){
                            successNum++;  
                        }
                        if(insert==0){
                            failureNum++;
                        }
                        
                    } else if (updateSupport) {
                        vehicle.setVehicleId(vehicleId);
                        //导入修改数据校验统计
                        int update = updateVehicleExcel(vehicle);
                        if(update>0){
                            successNum++;
                        }
                        if(update==0){
                            failureNum++;
                        }
                    }
                } catch (Exception e) {
                    if (!firstErr) {
                        errorMsg = "导入" + vehicle.getCarNum() + "出现异常：" + e.getMessage();
                        firstErr = true;
                    }
                    failureNum++;
                }
            }
            StringBuilder msg = new StringBuilder();
            msg.append("成功导入：").append(successNum).append("条数据，失败导入：").append(failureNum)
                    .append("条数据，包含已存在车辆").append(repeatNum).append("条。");
            if (errorMsg != null) {
                msg.append("最近一次失败原因：").append(errorMsg);
            }
            return msg.toString();
        }
    }

    /**
     * 复制对象null覆盖处理
     *
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }



    @Override
    public List<Vehicle> queryVehiclesByCompanyId(Long companyId) {
        return vehicleMapper.queryVehicleByCompanyId(companyId);
    }

}
