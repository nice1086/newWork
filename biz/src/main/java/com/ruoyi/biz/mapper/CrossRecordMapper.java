package com.ruoyi.biz.mapper;

import com.ruoyi.biz.hik.response.CrossRecordResponse;
import org.apache.ibatis.annotations.Param;

/**
 * 唐野 2023年7月20日
 */
public interface CrossRecordMapper {
    CrossRecordResponse queryOneRecord(@Param("crossRecordSyscode") String crossRecordSyscode);

    int insertRecord(@Param("record") CrossRecordResponse crossRecordResponse);
}
