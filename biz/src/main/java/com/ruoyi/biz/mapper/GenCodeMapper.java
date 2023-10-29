package com.ruoyi.biz.mapper;

import com.ruoyi.biz.domain.PO.GenCode;
import org.apache.ibatis.annotations.Param;

/**
 * @author MR.ZHAO
 * @since 2023/7/14
 */
public interface GenCodeMapper {

    /**
     * 获取下一个编号
     * @param codeId 自动编号序号
     * @return 自动编号
     */
    GenCode nextCode(@Param("codeId") Long codeId);

    /**
     * 编号加一
     * @param codeId 自动编码序号
     */
    void updateNum(@Param("codeId") Long codeId);

}
