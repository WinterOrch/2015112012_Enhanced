package com.tool.security;

import com.system.PropertiesLocale;
import com.system.constant.SystemConstant;

import static com.system.PropertiesLocale.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A Generally Used Tool Class For Encryption And Decryption
 * @author Frankel.Y
 * Created in 0:06 2018/7/14
 */
public class KeyPairUtility {
    public static int DH_KEY_SIZE = 1024;
    public static int RANDOM_KEY_SIZE = 25;
    public static final String DH_PUBLIC_KEY = "DH_Public_Key";
    public static final String DH_PRIVATE_KEY = "DH_Private_Key";

    public static final String SESSION_KEY = "_Session_Key";

    private static final String SECRET_KEY_FILE = "skf";

    public static byte[] getDHPublicKey(Map<String,Object> map) {
        return ((DHPublicKey) map.get(DH_PUBLIC_KEY)).getEncoded();
    }

    public static byte[] getDHPrivateKey(Map<String,Object> map) {
        return ((DHPrivateKey) map.get(DH_PRIVATE_KEY)).getEncoded();
    }

    public static Key getSessionKey(Map<String,Object> map, String algorithm) {
        return ((SecretKey) map.get(algorithm + SESSION_KEY));
    }

    public static Key generateSecretKey(String password, String algorithm) {

        for(int i = 0; i < SystemConstant.SYMMETRIC_ALGORITHM.length; i++) {
            if(SystemConstant.SYMMETRIC_ALGORITHM[i].equals(algorithm)) {
                KeyGenerator kGen;
                try {
                    kGen = KeyGenerator.getInstance(algorithm);
                    kGen.init(SystemConstant.SYMMETRIC_KEY_SIZE[i], new SecureRandom(password.getBytes()));

                    return kGen.generateKey();
                } catch (NoSuchAlgorithmException e) {

                    e.printStackTrace();
                }
            }
        }

        return null;
    }
    /**
     * Save Key Map As File
     * @author Frankel.Y
     * Created in 0:15 2018/7/14
     */
    public static void saveSecretKeyFile(Map<String,Object> key) {
        // Initialize JFileChooser
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                getProperty("SECURITY.ENCRYPTION.UTILITY.SKF") + SECRET_KEY_FILE + ")", SECRET_KEY_FILE);
        chooser.setFileFilter(filter);

        int option = chooser.showSaveDialog(null);
        if(option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String fileName = chooser.getName(file);

            if(!fileName.contains( "." + SECRET_KEY_FILE )){
                file = new File(chooser.getCurrentDirectory(),fileName+"." + SECRET_KEY_FILE);
            }

            // Object Stream Output
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(file));
                Objects.requireNonNull(oos).writeObject(key);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Close Stream
                try {
                    Objects.requireNonNull(oos).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Read Secret Key File
     * @author Frankel.Y
     * Created in 0:23 2018/7/14
     */
    public static Map<String, Object> getKeyMap() {
        Map<String, Object> key = new HashMap<>();

        // Initialize JFileChooser
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                getProperty("SECURITY.ENCRYPTION.UTILITY.SKF") + SECRET_KEY_FILE + ")", SECRET_KEY_FILE);
        chooser.setFileFilter(filter);
        chooser.setDialogTitle(PropertiesLocale.getProperty("UI.SYMMETRIC.ENCRYPT.SK.SELECT"));

        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String fileName = chooser.getName(file);

            if (!fileName.contains("." + SECRET_KEY_FILE)) {
                file = new File(chooser.getCurrentDirectory(), fileName + "." + SECRET_KEY_FILE);
            }

            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(new FileInputStream(file));
                //noinspection unchecked
                key = (Map<String, Object>) ois.readObject();
                ois.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return key;
        }else {

            return null;
        }
    }

}
