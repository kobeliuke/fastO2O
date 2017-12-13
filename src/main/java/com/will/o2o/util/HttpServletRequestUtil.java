package com.will.o2o.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpServletRequestUtil.class);

    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            logger.error("failed to decode String: " + e.getMessage());
            return -1;
        }
    }

    public static Long getLong(HttpServletRequest request, String key) {
        try {
            return Long.decode(request.getParameter(key));
        } catch (Exception e) {
            logger.error("failed to decode String: " + e.getMessage());
            return -1L;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            logger.error("failed to decode String: " + e.getMessage());
            return -1d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            logger.error("failed to decode String: " + e.getMessage());
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                result = result.trim();
            }
            if ("".equals(result)) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }

    }

}
