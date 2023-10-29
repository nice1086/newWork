package com.ruoyi.biz.util.validation;

import cn.hutool.core.util.StrUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

/**
 * @author MR.ZHAO
 * @since 2023/7/13
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Phone.PhoneValidation.class})
public @interface Phone {

    String message() default "手机号格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PhoneValidation implements ConstraintValidator<Phone, String> {

        private static final Pattern PATTERN=Pattern.compile("^1[3-9]\\d{9}$");

        @Override
        public void initialize(Phone constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (StrUtil.isEmpty(value)) {
                return false;
            } else {
                return isPhone(value);
            }
        }

        /**
         * 验证手机号
         */
        public static boolean isPhone(String iphone){
            if (StrUtil.isEmpty(iphone)) {
                return false;
            } else {
                return PATTERN.matcher(iphone).matches();
            }
        }
    }

}


