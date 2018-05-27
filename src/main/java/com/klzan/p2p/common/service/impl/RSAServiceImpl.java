/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.common.service.impl;

import com.klzan.core.util.RSAUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.RSAService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @ClassName : RSAServiceImpl
 * @Description: RSA安全
 * @Author suhao
 * @Version 1.0
 */
@Service
public class RSAServiceImpl implements RSAService {
    public static final String KEY_ATTR = "oKey";

    @Override
    public RSAPublicKey generateKey(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 已存在密钥
        HttpSession session = request.getSession();
        KeyPair sKeyPair = (KeyPair) session.getAttribute(KEY_ATTR);
        if (sKeyPair != null) {
            return (RSAPublicKey) sKeyPair.getPublic();
        }
        // 生成密钥
        KeyPair keyPair = RSAUtils.generateKeyPair();
        session.setAttribute(KEY_ATTR, keyPair);
        return (RSAPublicKey) keyPair.getPublic();
    }

    @Override
    public void removePrivateKey(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        session.removeAttribute(KEY_ATTR);
    }

    @Override
    public String decryptParameter(String name, HttpServletRequest request) {
        if (StringUtils.isBlank(name) || request == null) {
            return null;
        }
        String parameter = request.getParameter(name);
        HttpSession session = request.getSession();
        KeyPair sKeyPair = (KeyPair) session.getAttribute(KEY_ATTR);
        if (StringUtils.isBlank(parameter) || sKeyPair == null) {
            return null;
        }
        RSAPrivateKey privateKey = (RSAPrivateKey) sKeyPair.getPrivate();
        return RSAUtils.decrypt(privateKey, parameter);
    }

}