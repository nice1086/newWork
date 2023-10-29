package com.ruoyi.common.exception.user;

/**
 * @author MR.ZHAO
 * @since 2023/7/27
 */
public class CompanyStatusException extends UserException{

    private static final long serialVersionUID = 1L;

    public CompanyStatusException() {
        super("login.companyStatus", null);
    }
}
