package com.tool.security.encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;

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
     * File Crypt
     * @param CipherMode    Encrypt:   Cipher.ENCRYPT_MODE
     *                       Decrypt:   Cipher.DECRYPT_MODE
     * @param srcFile       File to be encrypted
     * @param desFile       FileStream Output Destination
     * @param algorithm     Encryption Algorithm Utilized
     * @param mod           Encryption Model
     * @param padding       Padding Method
     */
    public static void crypt(int CipherMode, File srcFile, File desFile, Key key, String algorithm, String mod, String padding)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(algorithm + "/" + mod + "/" + padding, "BC");
        cipher.init(CipherMode, key);

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(desFile));

            crypt(fis, fos, cipher);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
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

    private static File mkdirFiles(File filePath) throws IOException {
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        filePath.createNewFile();

        return filePath;
    }

    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1000;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
    }
}
