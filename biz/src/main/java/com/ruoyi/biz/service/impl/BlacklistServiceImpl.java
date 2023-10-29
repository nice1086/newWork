package com.ruoyi.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.biz.domain.PO.Blacklist;
import com.ruoyi.biz.domain.VO.TransportCompanyVO;
import com.ruoyi.biz.domain.Vehicle;
import com.ruoyi.biz.hik.response.AlarmCarQueryResponse;
import com.ruoyi.biz.hik.service.CarBlackRemoteService;
import com.ruoyi.biz.mapper.BlacklistMapper;
import com.ruoyi.biz.service.IBlacklistService;
import com.ruoyi.biz.service.ITransportCompanyService;
import com.ruoyi.biz.service.IVehicleService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * 黑名单Service业务层处理
 * 
 * @author yizhi
 * @date 2023-07-13
 */
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements IBlacklistService
{
    @Autowired
    private BlacklistMapper blacklistMapper;

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private ITransportCompanyService transportCompanyService;

    @Autowired
    private CarBlackRemoteService carBlackRemoteService;

    /**
     * 查询黑名单
     * 
     * @param blacklistId 黑名单主键
     * @return 黑名单
     */
    @Override
    public Blacklist selectBlacklistByBlacklistId(Long blacklistId)
    {
        return blacklistMapper.selectBlacklistByBlacklistId(blacklistId);
    }

    /**
     * 查询黑名单列表
     * 
     * @param blacklist 黑名单
     * @return 黑名单
     */
    @Override
    public List<Blacklist> selectBlacklistList(Blacklist blacklist)
    {
        return blacklistMapper.selectBlacklistList(blacklist);
    }

    /**
     * 新增黑名单
     * 
     * @param blacklist 黑名单
     * @return 结果
     */
    @Override
    public int insertBlacklist(Blacklist blacklist)
    {
        blacklist.setCreateTime(DateUtils.getNowDate());
        // 判断封禁方式
        if(CharSequenceUtil.equals(blacklist.getBlacklistType(), "承运企业")){
            blacklist.setBlacklistScoop(1);
            // 查询企业信息
            TransportCompanyVO transportCompanyVO = transportCompanyService.selectCompanyByCompanyId(blacklist.getCompanyId());
            blacklist.setBlacklistName(transportCompanyVO.getCompanyName());
            // 查询承运企业下的所有车辆
            List<Vehicle> vehicles = vehicleService.queryVehiclesByCompanyId(blacklist.getCompanyId());
            // 批量添加车辆黑名单
            vehicles.forEach(item->{
                Blacklist bk = new Blacklist();
                BeanUtils.copyProperties(blacklist, bk);
                bk.setBlacklistName(item.getCarNum());
                bk.setBlacklistType("车辆");
                bk.setCompanyId(blacklist.getCompanyId());
                bk.setBlacklistScoop(1);
                insertOrUpdateCar(bk);
            });
            // 单独插入企业信息
            insertOrUpdateCompany(blacklist);
        }else{
            //调用第三方接口通知海康拉黑车辆
            blacklist.setBlacklistScoop(0);
            insertOrUpdateCar(blacklist);
        }
        return 1;
    }

    private void insertOrUpdateCompany(Blacklist blacklist){
        Blacklist exist = blacklistMapper.queryByCarNum(blacklist.getBlacklistName());
        if(ObjectUtil.isNotEmpty(exist)){
            // 更新
            blacklist.setBlacklistId(exist.getBlacklistId());
            blacklistMapper.updateBlacklist(blacklist);
            return;
        }
        blacklistMapper.insertBlacklist(blacklist);
    }

    /**
     * 修改黑名单
     * 
     * @param blacklist 黑名单
     * @return 结果
     */
    @Override
    public int updateBlacklist(Blacklist blacklist) {
        blacklist.setUpdateTime(DateUtils.getNowDate());
        // 原本封禁，现在解禁，走删除黑名单
        if(blacklist.getBlacklistStatus()==1){
            return deleteBlacklistByBlacklistId(blacklist.getBlacklistId());
        }
        // 原本解禁，现在封禁
        // 判断封禁方式
        if(CharSequenceUtil.equals(blacklist.getBlacklistType(), "承运企业")){
            blacklist.setBlacklistScoop(1);
            // 查询承运企业下的所有车辆
            List<Vehicle> vehicles = vehicleService.queryVehiclesByCompanyId(blacklist.getCompanyId());
            // 批量添加车辆黑名单
            vehicles.forEach(item->{
                Blacklist bk = new Blacklist();
                BeanUtils.copyProperties(blacklist, bk);
                bk.setBlacklistName(item.getCarNum());
                bk.setBlacklistType("车辆");
                bk.setCompanyId(blacklist.getCompanyId());
                insertOrUpdateCar(bk);
            });
            // 单独更新企业信息
            blacklistMapper.updateBlacklist(blacklist);
        }else{
            //调用第三方接口通知海康拉黑车辆
            blacklist.setBlacklistScoop(0);
            insertOrUpdateCar(blacklist);
        }
        return 1;
    }


    private void insertOrUpdateCar(Blacklist blacklist){
        // 查询车辆是否存在黑名单中
        Blacklist alarmCar = blacklistMapper.queryByCarNum(blacklist.getBlacklistName());
        if(ObjectUtil.isNotEmpty(alarmCar)){
            blacklist.setBlacklistId(alarmCar.getBlacklistId());
            blacklist.setAlarmSyscode(alarmCar.getAlarmSyscode());
            //更新
            // 时间对比，取封锁时间长的
            if(alarmCar.getBlacklistScoop()==0){
                Date finalDate = DateUtil
                        .compare(alarmCar.getEndTime(), blacklist.getEndTime())>0 ? alarmCar.getEndTime():blacklist.getEndTime();
                blacklist.setEndTime(finalDate);
            }
            blacklist.setBlacklistScoop(blacklist.getBlacklistScoop());
            blacklist.setCompanyId(blacklist.getCompanyId());
            updateSingleCar(blacklist);
            return;
        }

        // 插入
        addSingleCar(blacklist);

    }

    private void addSingleCar(Blacklist blacklist){
        //调用第三方接口通知海康拉黑车辆
        blacklist.setBlacklistScoop(0);
        ZonedDateTime endTime =
                ZonedDateTime.ofInstant(blacklist.getEndTime().toInstant(), ZoneId.systemDefault());
        String alarmSyscode = carBlackRemoteService.addCarBlack(blacklist.getBlacklistName(), endTime);
        if(StringUtils.isNotEmpty(alarmSyscode)){
            blacklist.setAlarmSyscode(alarmSyscode);
            blacklist.setCreateTime(DateUtils.getNowDate());
            blacklistMapper.insertBlacklist(blacklist);
        }
    }

    /**
     * 单独更新一个车辆
     * @param blacklist
     */
    private void updateSingleCar(Blacklist blacklist){
        //调用第三方接口通知海康修改拉黑车辆
        // 解除黑名单
        if(blacklist.getBlacklistStatus()==0){
            boolean b = carBlackRemoteService.delCarBlack(blacklist.getAlarmSyscode());
            if(!b){
                throw new ServiceException("处理失败，请稍后再试");
            }
        }
        ZonedDateTime endTime =
                ZonedDateTime.ofInstant(blacklist.getEndTime().toInstant(), ZoneId.systemDefault());
        String data = carBlackRemoteService.addCarBlack(blacklist.getBlacklistName(), endTime);
        if(StringUtils.isNotEmpty(data)){
            blacklist.setBlacklistStatus(0L);
            blacklist.setAlarmSyscode(data);
            blacklist.setUpdateTime(new Date());
            blacklistMapper.updateBlacklist(blacklist);
        }
    }

    /**
     * 修改黑名单状态
     *
     * @param blacklist 黑名单
     * @return 结果
     */
    @Override
    public int updateBlacklistStatus(Blacklist blacklist) {
        return updateBlacklist(blacklist);
    }

    /**
     * 批量删除黑名单
     * 
     * @param blacklistIds 需要删除的黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBlacklistByBlacklistIds(Long[] blacklistIds)
    {
        return blacklistMapper.deleteBlacklistByBlacklistIds(blacklistIds);
    }

    /**
     * 删除黑名单信息
     * 
     * @param blacklistId 黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBlacklistByBlacklistId(Long blacklistId)
    {
        // 判断封禁方式
        Blacklist blacklist = blacklistMapper.selectBlacklistByBlacklistId(blacklistId);
        if(ObjectUtil.isEmpty(blacklist)){
            throw new ServiceException("该企业未被拉黑或已经解除拉黑");
        }
        if(blacklist.getBlacklistScoop()==1){
            // 企业解禁
            List<Blacklist> blacklists = blacklistMapper.queryByCompanyId(blacklist.getCompanyId());
            // 批量添加车辆黑名单
            blacklists.forEach(item->{
                //调用第三方接口通知海康修改拉黑车辆
                if(carBlackRemoteService.delCarBlack(item.getAlarmSyscode())){
                    blacklistMapper.logicDeleteBlackList(item.getBlacklistId());
                }
            });
        }else{
            //调用第三方接口通知海康修改拉黑车辆
            carBlackRemoteService.delCarBlack(blacklist.getAlarmSyscode());
        }
        blacklistMapper.logicDeleteBlackList(blacklistId);
        return 1;
    }

    @Override
    public int syncBlacklist(AlarmCarQueryResponse alarmCarQueryResponse) {
        String alarmSyscode = alarmCarQueryResponse.getAlarmSyscode();
        Blacklist blacklist = blacklistMapper.queryBlackListByAlarmSyscode(alarmSyscode);
        if(ObjectUtil.isEmpty(blacklist)){
            // 查不到，添加这条记录
            Blacklist bk = new Blacklist();
            bk.setBlacklistScoop(0);
            bk.setAlarmSyscode(alarmCarQueryResponse.getAlarmSyscode());
            bk.setBlacklistType("车辆");
            bk.setBlacklistName(alarmCarQueryResponse.getPlateNo());
            bk.setStartTime(new Date());
            bk.setRemark("同步数据");
            if(ObjectUtil.isEmpty(alarmCarQueryResponse.getEndTime())){
                bk.setEndTime(DateUtil.parse("9999-12-31", "yyyy-MM-dd"));
            }else{
                bk.setEndTime(DateUtil.parse(alarmCarQueryResponse.getEndTime()));
            }
            bk.setBlacklistStatus(0L);
            bk.setReason(alarmCarQueryResponse.getRemark());
            blacklistMapper.insertBlacklist(bk);
            return 1;
        }else{
            // 修改数据库为同步状态
            blacklistMapper.confirmBlackList(alarmSyscode);
        }

        return 0;
    }

    @Override
    public int setAllBlacklistReady() {
        blacklistMapper.resetAll();
        return 0;
    }

    @Override
    public int setReadyToRelease() {
        blacklistMapper.releaseAll();
        return 0;
    }

    @Override
    public int confirmBlackList(String alarmSyscode) {
        blacklistMapper.confirmBlackList(alarmSyscode);
        return 0;
    }

    @Override
    public Blacklist queryBlacklistByCarNum(String carNum) {
        return blacklistMapper.queryByCarNum(carNum);
    }
}
