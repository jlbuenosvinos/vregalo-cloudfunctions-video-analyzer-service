package com.google.jbuenosv.inditex.poc.vregalo.cloudfunctions.infrastructure.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by jbuenosv@google.com
 */
public class UUIDGenerator {

    public String getUuid() {
        MessageDigest salt = null;
        try {
            salt = MessageDigest.getInstance("SHA-256");
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        return hexEncode(salt.digest());
    }

    private String hexEncode(byte[] aInput){
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b&0xf0) >> 4 ]);
            result.append(digits[ b&0x0f]);
        }
        return result.toString();
    }
}
