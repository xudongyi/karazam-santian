/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.common;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.util.ConstantUtils;
import com.klzan.p2p.model.base.BaseUserModel;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static String algorithmName = ConstantUtils.HASH_ALGORITHM;

    private static int hashIterations = ConstantUtils.HASH_INTERATIONS;

    public static void encryptPassword(BaseUserModel user, String toEncryptPassword) {
        String salt = randomNumberGenerator.nextBytes().toHex();
        String newPassword = new SimpleHash(algorithmName, toEncryptPassword, ByteSource.Util.bytes(salt), hashIterations).toHex();
        user.updateSaltAndPassword(salt, newPassword);
    }

    public static boolean verifyPassword(BaseUserModel user, String loginPwd) {
        String password = user.getPassword();
        loginPwd = new SimpleHash(algorithmName, loginPwd, ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
        if (StringUtils.equals(password, loginPwd)) {
            return true;
        }
        return false;
    }

    public static String encryptPassword(String toEncryptPassword, String salt) {
        return new SimpleHash(algorithmName, toEncryptPassword, ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

}
