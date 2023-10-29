package com.ruoyi.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.biz.domain.PO.Blacklist;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * 黑名单Mapper接口
 * 
 * @author yizhi
 * @date 2023-07-13
 */
public interface BlacklistMapper extends BaseMapper<Blacklist>
{
    /**
     * 查询黑名单
     * 
     * @param blacklistId 黑名单主键
     * @return 黑名单
     */
    public Blacklist selectBlacklistByBlacklistId(Long blacklistId);

    /**
     * 查询黑名单列表
     * 
     * @param blacklist 黑名单
     * @return 黑名单集合
     */
    public List<Blacklist> selectBlacklistList(Blacklist blacklist);

    /**
     * 新增黑名单
     * 
     * @param blacklist 黑名单
     * @return 结果
     */
    public int insertBlacklist(Blacklist blacklist);

    /**
     * 修改黑名单
     * 
     * @param blacklist 黑名单
     * @return 结果
     */
    public int updateBlacklist(Blacklist blacklist);

    /**
     * 删除黑名单
     * 
     * @param blacklistId 黑名单主键
     * @return 结果
     */
    public int deleteBlacklistByBlacklistId(Long blacklistId);

    /**
     * 批量删除黑名单
     * 
     * @param blacklistIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBlacklistByBlacklistIds(Long[] blacklistIds);

    List<Blacklist> queryBlackCarsByCompanyId(Long companyId);

    int logicDeleteBlackList(Long blacklistId);

    Blacklist queryBlackListByAlarmSyscode(String alarmSyscode);

    int resetAll();

    int releaseAll();


    int confirmBlackList(String alarmSyscode);

    List<Blacklist> queryByCompanyId(Long companyId);

    Blacklist queryByCarNum(@Param("carNum") String carNum);

    Blacklist queryEffectiveBlacklistCar(String carNum);
}
