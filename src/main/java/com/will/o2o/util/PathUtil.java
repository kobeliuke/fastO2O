package com.will.o2o.util;

import java.io.File;

public class PathUtil {
    private static String separator = File.separator;

    //also can get file separator with this method;
    //private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = null;
        if (os.toLowerCase().startsWith("win")) {
            //TODO：use config file to configure this later
            basePath = "C:/o2oProjectResources/images";
        } else {
            //TODO: use config file to configure this later
            basePath = "/Users/willliu/Documents/o2oProjectResources/images";
        }
        basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopRelativeImgPath(long shopId) {
        String imgPath = "/upload/items/shop/" + shopId + "/";
        return imgPath.replace("/", separator);
    }
}
