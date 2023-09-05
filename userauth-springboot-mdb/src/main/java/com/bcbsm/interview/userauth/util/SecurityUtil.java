package com.bcbsm.interview.userauth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Utility class to encode and decode a string
 * @author Moorthi Ramasamy
 */
@Slf4j
@Service
public class SecurityUtil {



    public static String encodeText(String password) {
        String encoded = new String(Base64.getEncoder().encode(password.getBytes()));
        return encoded;
    }
    public static String decodeText(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }
    public String encodeDecode(String password)throws UnsupportedEncodingException {
        String encoded = encodeText(password);
        String decoded = decodeText(encoded);
        System.out.println("password: " + password + " -> " + encoded + " -> " + decoded);
        return decoded;
    }
}
