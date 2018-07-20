package com.system;

import com.system.constant.SystemConstant;

import java.io.*;
import java.util.Properties;

/**
 * Read Properties
 * @author Frankel.Y
 * Created in 20:08 2018/4/30
 */
public class PropertiesLocale {
    public static int locale;

    /**
     * Initialize Config to Select The Language
     * created in 20:39 2018/4/30
     */
    public static void initialize(){
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(SystemConstant.CONFIG_PROPERTY));
            pps.load(in);
            switch (pps.getProperty("LANGUAGE")) {
                case "CN":
                    locale = 1;
                    break;
                case "EN":
                    locale = 2;
                    break;
                default:
                    locale = 1;
                    changeLanguage("CN");
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Config Information From Properties File
     * created in 20:42 2018/4/30
     */
    public static String getConfig(String key) {
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(SystemConstant.CONFIG_PROPERTY));
            pps.load(in);
            return pps.getProperty(key);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get Property From Properties File
     * created in 20:44 2018/4/30
     */
    public static String getProperty(String key){
        Properties pps = new Properties();
        if(locale==2){
            try {
                InputStream in = new BufferedInputStream(new FileInputStream(SystemConstant.EN_PATH_PROPERTY));
                pps.load(in);
                return pps.getProperty(key);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else{
            try {
                InputStream in = new BufferedInputStream(new FileInputStream(SystemConstant.CN_PATH_PROPERTY));
                pps.load(in);
                return pps.getProperty(key);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Change UI Language And Rewrite The Config File
     * created in 20:48 2018/4/30
     */
    public static void changeLanguage(String language){
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(SystemConstant.CONFIG_PROPERTY));
            props.load(in);
            OutputStream fos = new FileOutputStream(SystemConstant.CONFIG_PROPERTY);
            props.setProperty("LANGUAGE", language);
            props.store(fos, "Update value");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error in updating language config files");
        }
    }

    /**
     * Change Algorithm And Rewrite The Config File
     * created in 16:13 2018/7/15
     */
    public static void changeAlgorithm(String type, String name){
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(SystemConstant.CONFIG_PROPERTY));
            props.load(in);
            OutputStream fos = new FileOutputStream(SystemConstant.CONFIG_PROPERTY);
            props.setProperty(type, name);
            props.store(fos, "Update value");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error in updating language config files");
        }
    }
}
