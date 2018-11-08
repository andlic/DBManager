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

    /**
     * Checks if SHA1 encryption equals given string.
     *
     * @param sha1 The encrypted value.
     * @param str  The value to compare to.
     * @return {@code True} if value equals SHA1 encryption, {@code False} otherwise.
     */
    public static boolean compareSHA1(String sha1, String str) {
        return encryptSHA1(str).equals(sha1);
    }

    /**
     * Checks if MD5 encryption equals given string.
     *
     * @param md5 The encrypted value.
     * @param str The value to compare to.
     * @return {@code True} if value equals MD% encryption, {@code False} otherwise.
     */
    public static boolean compareMD5(String md5, String str) {
        return encryptMD5(str).equals(md5);
    }

}