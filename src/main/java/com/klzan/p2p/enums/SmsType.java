package com.klzan.p2p.enums;

/**
 * 短信类型
 */
public enum SmsType implements IEnum {

    USER_REGIST_CODE("注册短信验证码"),

    USER_FIND_PASSWORD_CODE("找回密码短信验证码"),

    USER_FIND_PAY_PASSWORD_CODE("找回支付密码短信验证码"),

    MODIFY_PAY_PASSWORD_CODE("修改支付密码短信验证码"),

    MODIFY_LOGIN_PASSWORD_NOTICE("修改登录密码通知"),

    FULL_NOTICE("满标短信提醒"),

    LENDING_NOTICE("放款短信提醒"),

    REPAYMENT_NOTICE("还款短信提醒"),

    BORROWING_APPLY_CODE("借款申请短信验证码"),

    OTHER("其他");

    private String displayName;

    SmsType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public TemplateContentType getTemplateContentType() {
        return TemplateContentType.SMS;
    }

    public TemplatePurpose getTemplatePurpose(){
        switch (this) {
            case USER_REGIST_CODE:
                return TemplatePurpose.PC_REGISTER_CODE_SMS;
            case USER_FIND_PASSWORD_CODE:
                return TemplatePurpose.PC_FIND_PWD_CODE_SMS;
            case USER_FIND_PAY_PASSWORD_CODE:
                return TemplatePurpose.PC_FIND_PAY_PWD_CODE_SMS;
            case MODIFY_PAY_PASSWORD_CODE:
                return TemplatePurpose.PC_MODIFY_PAY_PWD_CODE_SMS;
            case MODIFY_LOGIN_PASSWORD_NOTICE:
                return TemplatePurpose.PC_MODIFY_LOGIN_PWD_CODE_SMS;
            case BORROWING_APPLY_CODE:
                return TemplatePurpose.PC_BORROWING_APPLY_CODE_SMS;
            default:
                return null;
        }
    }

}

