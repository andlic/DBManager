package net.lecuay.security;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * This class is used to encrypt text.
 * 
 * @author LeCuay
 * @version 0.1 - Alpha
 */
public class Encryption {

    /**
     * Encrypts String with MD5.
     * 
     * @param str The text to encrypt.
     * @return Encrypted text with MD5.
     * 
     * @throws RuntimeException If encryption fails.
     */
    public static String encryptMD5(String str) {
        try {
            BigInteger number = new BigInteger(1, MessageDigest.getInstance("MD5").digest(str.getBytes()));
            String hashtext = number.toString(0);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypts String with SHA1.
     * 
     * @param str The text to encrypt.
     * @return Encrypted text with SHA1.
     * 
     * @throws RuntimeException If encryption fails.
     */
    public static String encryptSHA1(String str) {
        try {
            byte[] result = MessageDigest.getInstance("SHA1").digest(str.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}