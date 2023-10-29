package com.ruoyi.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.biz.domain.SysNoticeAdd;
import com.ruoyi.biz.domain.SysNoticeDTO;
import com.ruoyi.biz.domain.SysNoticeVO;
import com.ruoyi.biz.service.ISysNoticeAddService;
import com.ruoyi.biz.mapper.SysNoticeAddMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class SysNoticeAddServiceImpl implements ISysNoticeAddService {

    @Autowired
    private SysNoticeAddMapper sysNoticeAddMapper;

    @Override
    public int noticeAdd(SysNoticeAdd sysNoticeAdd) {
        return sysNoticeAddMapper.noticeAdd(sysNoticeAdd);
    }

    @Override
    public int updateStatus(Long user_id, Long notice_id) {
        return sysNoticeAddMapper.updateStatus(user_id,notice_id);
    }

    @Override
    public void noticeUserAdd(SysNoticeDTO sysNoticeDTO) {
         sysNoticeAddMapper.noticeUserAdd(sysNoticeDTO);
    }


    @Override
    public List<SysNoticeVO> selectAdminNotice() {
        return sysNoticeAddMapper.selectAdminNotice();
    }

    @Override
    public List<SysNoticeVO> selectUserNotice(Long userId) {
        return sysNoticeAddMapper.selectUserNotice(userId);
    }

    @Override
    public int readNotice(Long noticeId) {
        return sysNoticeAddMapper.readNotice(noticeId);
    }

    @Override
    public SysNoticeVO noticeDetail(Long noticeId) {
        return sysNoticeAddMapper.noticeDetail(noticeId);
    }

    @Override
    public Long unread(Long userId) {
        return sysNoticeAddMapper.countUnread(userId);
    }

    @Override
    public SysNoticeVO draftDetail(String createBy) {
        return sysNoticeAddMapper.draftDatail(createBy);
    }

    @Override
    public SysNoticeVO adminReadDetail(Long noticeId) {
        return sysNoticeAddMapper.adminNoticeDetail(noticeId);
    }

    @Override
    public int deleteDraft(String createBy) {
        return sysNoticeAddMapper.deleteDraft(createBy);
    }

    @Override
    public boolean saveBatch(Collection<SysNoticeAdd> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<SysNoticeAdd> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<SysNoticeAdd> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(SysNoticeAdd entity) {
        return false;
    }

    @Override
    public SysNoticeAdd getOne(Wrapper<SysNoticeAdd> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<SysNoticeAdd> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<SysNoticeAdd> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<SysNoticeAdd> getBaseMapper() {
        return null;
    }

    @Override
    public Class<SysNoticeAdd> getEntityClass() {
        return null;
    }
}
