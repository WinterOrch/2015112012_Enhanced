package com.system.constant;

import java.io.File;

public class SystemConstant {

    public final static String APP_NAME = "Malice";
    public final static String APP_VERSION = "V_0.10";

    private final static String CURRENT_DIR = System.getProperty("user.dir");

    public final static String CONFIG_PROPERTY = CURRENT_DIR + File.separator + "config" + File.separator
            + "config.properties";
    public final static String CN_PATH_PROPERTY = CURRENT_DIR + File.separator + "config" + File.separator
            + "language-zh-cn.properties";
    public final static String EN_PATH_PROPERTY = CURRENT_DIR + File.separator + "config" + File.separator
            + "language-en-ww.properties";

    public final static String [ ] LANGUAGE_LIST = {"中文 (zh-CN)","English (en-WW)"};
}
