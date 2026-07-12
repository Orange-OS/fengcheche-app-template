package com.zsy.framework.common.exception.common;

public class ExceptionUtil {

    public static String formatMessage(String template, Object... args) {
        if (args == null || args.length == 0) {
            return template;
        }

        String result = template;
        for (Object arg : args) {
            result = result.replaceFirst("\\{}", String.valueOf(arg));
        }
        return result;
    }
}
