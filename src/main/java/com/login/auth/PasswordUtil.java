package com.login.auth;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

/**
 * Utility for Password security.
 *
 * @author : Jemin
 */
public class PasswordUtil {
    // The higher the number of iterations the more expensive computing the hash for attacker and yes for us as well.
    private static final int iterations = 1000;
    private static final int saltLen = 16;
    private static final int desiredKeyLen = 256;

    public static void main(String[] args) throws Exception {
        System.out.println(getSaltedHash("hello123"));
    }

    /**
     * Generate salt hash of given plain password.
     * 1) Generate the salt.
     * 2) Generate the hash.
     * 3) Combine both with $.
     */
    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /**
     * Check given plain password is same as salt hash or not.
     *
     * @param password :  Plain password.
     * @param stored   : salt hash of password.
     * @return : True or false.
     * @throws Exception
     */
    public static boolean check(String password, String stored) throws Exception {
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    /**
     * Generate the Hash of plain password using PBKDF2 from sun.
     *
     * @param password : Plain password.
     * @param salt     : Salt.
     * @return : Salt hash password.
     * @throws Exception
     */
    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen)
        );
        return Base64.encodeBase64String(key.getEncoded());
    }
}
