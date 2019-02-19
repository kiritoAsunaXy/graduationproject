package com.xuyu.springboot.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class TestMD5 {

    public static String encryPassword(String password, String username) {
        String hashAlgorithmName = "MD5";
        Object credentials = password;
        Object salt = ByteSource.Util.bytes(username);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        return result.toString();


    }
}

