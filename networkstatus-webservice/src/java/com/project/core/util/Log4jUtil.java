/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author SAM
 */
public class Log4jUtil {

    static private final Logger logger = LogManager.getLogger(Log4jUtil.class.getName());

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void trace(String message) {
        logger.trace(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public synchronized static void fatal(Exception e) {
        logger.fatal(getFormattedMessageFromStackTraceElements(e));
    }

    public static String getFormattedMessageFromStackTraceElements(Exception e) {
        StringBuilder message = new StringBuilder();
        message.append("Message : ").append(e.getMessage());
        message.append(" --> ");
        StackTraceElement[] traceElements = e.getStackTrace();
        for (StackTraceElement traceElement : traceElements) {
            message.append(" Class Name : ").append(traceElement.getClassName());
            message.append("; Line No. :").append(traceElement.getLineNumber());
            message.append("; Method Name :").append(traceElement.getMethodName());
            message.append(" >>> ");
        }
        message.append(" ---- EOL. <<<");
        return message.toString();
    }
}
