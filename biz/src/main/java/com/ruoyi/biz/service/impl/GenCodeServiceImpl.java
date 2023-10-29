package com.ruoyi.biz.service.impl;

import com.ruoyi.biz.domain.PO.GenCode;
import com.ruoyi.biz.mapper.GenCodeMapper;
import com.ruoyi.biz.service.IGenCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author MR.ZHAO
 * @since 2023/7/14
 */
@Service
public class GenCodeServiceImpl implements IGenCodeService {

    @Autowired
    private GenCodeMapper genCodeMapper;

    /**
     * 获取下一个编号
     * @param codeId 自动编号序号
     * @return 自动编号
     */
    @Override
    @Transactional
    public synchronized String nextCode(Long codeId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        GenCode nextCode = genCodeMapper.nextCode(codeId);
        genCodeMapper.updateNum(codeId);
        return nextCode.getCodePrefix() + sdf.format(new Date()) + String.format("%05d", nextCode.getNextNum());
    }

}
