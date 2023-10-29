package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.Vehicle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 车辆Mapper接口
 * 
 * @author yizhi
 * @date 2023-07-13
 */
public interface VehicleMapper extends BaseMapper<Vehicle>
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
     * 新增车辆
     * 
     * @param vehicle 车辆
     * @return 结果
     */
    public int insertVehicle(Vehicle vehicle);

    /**
     * 修改车辆
     * 
     * @param vehicle 车辆
     * @return 结果
     */
    public int updateVehicle(Vehicle vehicle);

    /**
     * 删除车辆
     * 
     * @param vehicleId 车辆主键
     * @return 结果
     */
    public int deleteVehicleByVehicleId(Long vehicleId);

    /**
     * 批量删除车辆
     * 
     * @param vehicleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVehicleByVehicleIds(Long[] vehicleIds);

    /**
     * 根据车牌号查询车辆
     *
     * @param carNum 车牌号
     * @return 车辆集合
     */
    Vehicle selectVehicleOneBycarNum(String carNum);

    List<Vehicle> queryVehicleByCompanyId(@Param("companyId") Long companyId);
}
