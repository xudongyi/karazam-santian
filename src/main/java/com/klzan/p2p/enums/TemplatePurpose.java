package com.klzan.p2p.enums;

/**
 * 模板用途
 */
public enum TemplatePurpose implements IEnum {
    PC_REGISTER_CODE_SMS("PC端注册验证码短信"),
    PC_BORROWING_APPLY_CODE_SMS("PC端借款申请验证码短信"),
    PC_FIND_PWD_CODE_SMS("PC端找回密码验证码短信"),
    PC_FIND_PAY_PWD_CODE_SMS("PC端找回支付密码验证码短信"),
    PC_MODIFY_PAY_PWD_CODE_SMS("PC端找回支付密码验证码短信"),
    PC_MODIFY_LOGIN_PWD_CODE_SMS("PC端修改登录密码通知短信");

    private String displayName;

    TemplatePurpose(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public SmsType getSmsType() {
        if (this == PC_REGISTER_CODE_SMS) {
            return SmsType.USER_REGIST_CODE;
        }
        if (this == PC_BORROWING_APPLY_CODE_SMS) {
            return SmsType.BORROWING_APPLY_CODE;
        }
        if (this == PC_FIND_PWD_CODE_SMS) {
            return SmsType.USER_FIND_PASSWORD_CODE;
        }
        if (this == PC_FIND_PAY_PWD_CODE_SMS) {
            return SmsType.USER_FIND_PAY_PASSWORD_CODE;
        }
        if (this == PC_MODIFY_LOGIN_PWD_CODE_SMS) {
            return SmsType.MODIFY_LOGIN_PASSWORD_NOTICE;
        }
        return SmsType.OTHER;
    }

}
