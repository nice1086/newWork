package com.ruoyi.biz.service.impl;

import com.ruoyi.biz.domain.Vedio;
import com.ruoyi.biz.mapper.VedioMapper;
import com.ruoyi.biz.service.IVedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VedioServiceImpl implements IVedioService {
    @Autowired
    private VedioMapper vedioMapper;

    @Override
    public List<Vedio> findAll() {
        return vedioMapper.findAll();
    }
}
