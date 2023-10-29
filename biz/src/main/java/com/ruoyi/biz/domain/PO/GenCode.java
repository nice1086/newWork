package com.ruoyi.biz.domain.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ChuanXiangZhao
 * @since 2023/7/14
 */
@TableName(value = "t_gen_code")
@Data
public class GenCode {

    /** 自动编码序号 **/
    @TableId(type = IdType.AUTO)
    private Long codeId;

    /** 自动编码前缀 **/
    private String codePrefix;

    /** 下一个编码号 **/
    private Long nextNum;

}
