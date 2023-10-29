package com.ruoyi.biz.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.ruoyi.common.exception.ServiceException;
import org.hibernate.validator.HibernateValidator;

/**
 * Validation手动校验
 * @author MR.ZHAO
 * @since 2023/7/20
 */
public class ValidatorUtils {

	private static final Validator VALIDATOR_FAST = Validation.byProvider(HibernateValidator.class).
            configure().failFast(true).buildValidatorFactory().getValidator();
    private static final Validator VALIDATOR_ALL = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(false).buildValidatorFactory().getValidator();

    /**
     * 校验遇到第一个不合法的字段直接返回不合法字段，后续字段不再校验
     */
    public static <T> void validateFast(T domain)  {
        Set<ConstraintViolation<T>> validateResult = VALIDATOR_FAST.validate(domain);
        if (validateResult.size() != 0) {
            throw new ServiceException(validateResult.iterator().next().getPropertyPath() +"："+ validateResult.iterator().next().getMessage());
        }
    }
    
    /**
     * 校验所有字段并返回不合法字段
     */
    public static <T> Set<ConstraintViolation<T>> validateAll(T domain) {
        return VALIDATOR_ALL.validate(domain);
    }
    
}
