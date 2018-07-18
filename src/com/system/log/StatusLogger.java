package com.system.log;

import com.system.constant.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusLogger {
    private static final Logger logger = LoggerFactory.getLogger(StatusLogger.class);

    public static void setStatusDetail(String statusDetail, Enum<LogLevel> level) {

        switch (level.toString()) {
            case "INFO":
                logger.info(statusDetail);
                break;
            case "DEBUG":
                logger.debug(statusDetail);
                break;
            case "WARN":
                logger.warn(statusDetail);
                break;
            case "ERROR":
                logger.error(statusDetail);
                break;
            case "FATAL":
                logger.error(statusDetail);
                break;
        }
    }
}
