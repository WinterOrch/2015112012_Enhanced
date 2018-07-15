package com.tool.security.encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SymEncrypt {
    /**
     * Encrypt
     * @param content       Content to be encrypted
     * @param secretKey     Secret Key Input
     * @param algorithm     Encryption Algorithm Utilized
     * @param mod           Encryption Model
     * @param padding       Padding Method
     * @return Cipher text
     */
    public static byte[] encrypt(byte[] content, SecretKey secretKey, String algorithm, String mod, String padding) {
        try {
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, algorithm);

            // Create And Initialize Cipher
            Cipher cipher = Cipher.getInstance(algorithm+"/"+mod+"/"+padding);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(content);
        } catch (NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypt
     * @param content       Cipher text
     * @param secretKey     Secret Key Input
     * @param algorithm     Encryption Algorithm Utilized
     * @param mod           Encryption Model
     * @param padding       Padding Method
     * @return              Clear text
     */
    public static byte[] decrypt(byte[] content, SecretKey secretKey, String algorithm, String mod, String padding) {
        try {
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, algorithm);

            // Create And Initialize Cipher
            Cipher cipher = Cipher.getInstance(algorithm+"/"+mod+"/"+padding);
            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
