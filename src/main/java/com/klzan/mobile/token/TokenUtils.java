package com.klzan.mobile.token;

import com.klzan.core.util.DigestUtils;
import com.klzan.p2p.model.AccessToken;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.token.AccessTokenService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenUtils {

    private final static String  USERKEY_SUFFIX = "us";

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 生成Token值
     * @param user
     * @return
     */
    public static String generateUserKey(User user){
        System.out.println("generateUserKey: " + DigestUtils.md5(user.getSalt() + user.getId() + USERKEY_SUFFIX));
        return DigestUtils.md5(user.getSalt() + user.getId() + USERKEY_SUFFIX);
    }

    public String generateToken(HttpServletRequest request){
        String sid = request.getSession().getId();
        System.out.println("generateToken: "+sid);
        Boolean isExist = accessTokenService.isExist(sid);
        int i=0;
        while (isExist){
            i++;
            if(i>10){
                throw new RuntimeException("系统错误");
            }
            sid = SessionUtil.generateSessionId();
            isExist = accessTokenService.isExist(sid);
            System.out.println("generateToken: "+sid);
        }
        return sid;
    }

}
