package com.ruoyi.biz.mapper;

import com.ruoyi.biz.domain.Vedio;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VedioMapper {
    List<Vedio> findAll();
}
