package com.ruoyi.biz.service;

/**
 * @author MR.ZHAO
 * @since 2023/7/14
 */
public interface IGenCodeService {

    /**
     * 获取下一个编号
     * @param codeId 自动编号序号
     * @return 自动编号
     */
    String nextCode(Long codeId);

}
