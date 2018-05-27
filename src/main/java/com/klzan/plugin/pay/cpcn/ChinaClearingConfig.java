package com.klzan.plugin.pay.cpcn;

/**
 * 公共接口参数 - 中金托管
 */
public class ChinaClearingConfig{

    public static String INSTITUTION_ID;

    public static String PAGE_URL;

    public static String PROJECT_URL;

    public static String PLAT_PAYMENT_ACCOUNT_NAME;

    public static String PLAT_PAYMENT_ACCOUNT_NUMBER;

//    public static String PLAT_FEE_PAYMENT_ACCOUNT_NAME;
//
//    public static String PLAT_FEE_PAYMENT_ACCOUNT_NUMBER;

    public void setInstitutionId(String institutionId) {
        INSTITUTION_ID = institutionId;
    }

    public void setPageUrl(String pageUrl) {
        PAGE_URL = pageUrl;
    }

    public void setProjectUrl(String projectUrl) {
        PROJECT_URL = projectUrl;
    }

    public void setPlatPaymentAccountName(String platPaymentAccountName) {
        PLAT_PAYMENT_ACCOUNT_NAME = platPaymentAccountName;
    }

    public void setPlatPaymentAccountNumber(String platPaymentAccountNumber) {
        PLAT_PAYMENT_ACCOUNT_NUMBER = platPaymentAccountNumber;
    }

//    public void setPlatFeePaymentAccountName(String platFeePaymentAccountName) {
//        PLAT_FEE_PAYMENT_ACCOUNT_NAME = platFeePaymentAccountName;
//    }
//
//    public void setPlatFeePaymentAccountNumber(String platFeePaymentAccountNumber) {
//        PLAT_FEE_PAYMENT_ACCOUNT_NUMBER = platFeePaymentAccountNumber;
//    }
}