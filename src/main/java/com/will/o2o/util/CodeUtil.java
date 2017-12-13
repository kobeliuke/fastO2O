package com.will.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        String verifyCodeActural = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        if (verifyCodeActural == null || !verifyCodeActural.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
