package com.klzan.p2p.freemarker.method;

import com.klzan.core.util.SecrecyUtils;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 模板方法 - 保密格式化
 */
@Component("secrecyMethod")
public class SecrecyMethod implements TemplateMethodModelEx {
    public final static Pattern MOBILE = Pattern.compile("1\\d{10}");

    @SuppressWarnings("rawtypes")
    public Object exec(List arguments) throws TemplateModelException {

        // 验证参数
        if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null
                && StringUtils.isNotBlank(arguments.get(0).toString())) {

            // 获取格式化类型
            String type = arguments.get(0).toString();

            // 格式化用户名
            if (StringUtils.equals(type, "username") && arguments.size() > 1 && arguments.get(1)!=null) {
                String username = arguments.get(1).toString();
                return new SimpleScalar(SecrecyUtils.toUsername(username, null));
            }

            // 格式化全名
            if (StringUtils.equals(type, "fullName") && arguments.size() > 1 && arguments.get(1)!=null) {
                String fullName = arguments.get(1).toString();
                return new SimpleScalar(SecrecyUtils.toName(fullName, null));
            }
            
            // 格式化姓名
            if (StringUtils.equals(type, "surname-name") && arguments.size() > 2 ) {
                String surname = arguments.get(1).toString();
                String name = arguments.get(2).toString();
                String replaceChar = arguments.get(3) != null ? arguments.get(3).toString() : null;
                return new SimpleScalar(SecrecyUtils.toName(surname, name, replaceChar));
            }

            // 格式化邮箱地址
            if (StringUtils.equals(type, "email") && arguments.size() > 1&& arguments.get(1)!=null) {
                String email = arguments.get(1).toString();
                return new SimpleScalar(SecrecyUtils.toEmail(email, null));
            }

            // 格式化手机号码
            if (StringUtils.equals(type, "mobile") && arguments.size() > 1&& arguments.get(1)!=null) {
                String mobile = arguments.get(1).toString();
                if (isMobile(mobile)) {
                    return new SimpleScalar(SecrecyUtils.toMobile(mobile, null));
                }
                return new SimpleScalar(SecrecyUtils.toUsername(mobile, null));
            }

            // 格式化身份证号码
            if (StringUtils.equals(type, "idNo") && arguments.size() > 1 && arguments.get(1)!=null) {
                String identity = arguments.get(1).toString();
                return new SimpleScalar(SecrecyUtils.toIdNo(identity, null));
            }
            
            // 格式化营业执照号
            if (StringUtils.equals(type, "corpLicenseNo") && arguments.size() > 1&& arguments.get(1)!=null) {
                String identity = arguments.get(1).toString();
                return new SimpleScalar(SecrecyUtils.toCorpLicenseNo(identity, null));
            }

            // 格式化公司名称
            if (StringUtils.equals(type, "corpName") && arguments.size() > 1&& arguments.get(1)!=null) {
                String identity = arguments.get(1).toString();
                return new SimpleScalar(SecrecyUtils.toCorpName(identity, null));
            }
            
            // 格式化银行卡号
            if (StringUtils.equals(type, "backcard") && arguments.size() > 1&& arguments.get(1)!=null) {
                String backcard = arguments.get(1).toString();
                return new SimpleScalar(SecrecyUtils.toBackCard(backcard, null));
            }

        }

        return null;
    }

    public static boolean isMobile(String value) {
        return isMactchRegex(MOBILE, value);
    }

    public static boolean isMactchRegex(Pattern pattern, String value) {
        return isMatch(pattern, value);
    }

    public static boolean isMatch(Pattern pattern, String content) {
        if(content == null || pattern == null) {
            //提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }

}