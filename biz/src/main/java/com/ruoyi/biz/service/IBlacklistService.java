package com.ruoyi.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.biz.domain.PO.Blacklist;
import com.ruoyi.biz.hik.response.AlarmCarQueryResponse;


import java.util.List;

/**
 * 黑名单Service接口
 * 
 * @author yizhi
 * @date 2023-07-13
 */
public interface IBlacklistService extends IService<Blacklist>
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
     * 修改黑名单状态
     *
     * @param blacklist 黑名单
     * @return 结果
     */
    public int updateBlacklistStatus(Blacklist blacklist);

    /**
     * 批量删除黑名单
     * 
     * @param blacklistIds 需要删除的黑名单主键集合
     * @return 结果
     */
    public int deleteBlacklistByBlacklistIds(Long[] blacklistIds);

    /**
     * 删除黑名单信息
     * 
     * @param blacklistId 黑名单主键
     * @return 结果
     */
    public int deleteBlacklistByBlacklistId(Long blacklistId);

    int syncBlacklist(AlarmCarQueryResponse alarmCarQueryResponse);

    int setAllBlacklistReady();

    int setReadyToRelease();

    int confirmBlackList(String alarmSyscode);

    Blacklist queryBlacklistByCarNum(String carNum);
}
