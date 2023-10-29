package com.ruoyi.biz.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.biz.domain.VO.TransportCompanyVO;
import com.ruoyi.biz.domain.Vehicle;

/**
 * 车辆Service接口
 * 
 * @author yizhi
 * @date 2023-07-13
 */
public interface IVehicleService extends IService<Vehicle>
{
    /**
     * 查询车辆
     * 
     * @param vehicleId 车辆主键
     * @return 车辆
     */
    public Vehicle selectVehicleByVehicleId(Long vehicleId);

    /**
     * 查询车辆列表
     * 
     * @param vehicle 车辆
     * @return 车辆集合
     */
    public List<Vehicle> selectVehicleList(Vehicle vehicle);


    /**
     * 根据车牌号查询车辆
     *
     * @param carNum 车牌号
     * @return 车辆集合
     */
    public Vehicle selectVehicleOneBycarNum(String carNum);

    /**
     * 新增车辆
     * 
     * @param vehicle 车辆
     * @return 结果
     */
    public int insertVehicle(Vehicle vehicle,boolean isImportExcel);

    /**
     * 新增车辆（导入用）
     *
     * @param vehicle 车辆
     * @return 结果
     */
    public int insertVehicleExcel(Vehicle vehicle,boolean isImportExcel);

    /**
     * 修改车辆
     * 
     * @param vehicle 车辆
     * @return 结果
     */
    public int updateVehicle(Vehicle vehicle);

    /**
     * 修改车辆(导入用)
     *
     * @param vehicle 车辆
     * @return 结果
     */
    public int updateVehicleExcel(Vehicle vehicle);

    /**
     * 批量删除车辆
     * 
     * @param vehicleIds 需要删除的车辆主键集合
     * @return 结果
     */
    public int deleteVehicleByVehicleIds(Long[] vehicleIds);

    /**
     * 删除车辆信息
     * 
     * @param vehicleId 车辆主键
     * @return 结果
     */
    public int deleteVehicleByVehicleId(Long vehicleId);

    /**
     * 查询运输企业
     * @return 车辆
     */
    List<TransportCompanyVO> selectVehicleCompanyList();

    /**
     * 导入车辆管理信息
     *
     * @param userList 车辆管理列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    String importUser(List<Vehicle> userList, boolean updateSupport);


    /**
     * 根据企业ID查询旗下所有车辆
     * @return
     */
    List<Vehicle> queryVehiclesByCompanyId(Long companyId);
}
