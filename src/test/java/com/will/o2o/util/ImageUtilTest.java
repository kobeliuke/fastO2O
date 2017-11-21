package com.will.o2o.util;

import com.will.o2o.BaseTest;
import org.junit.Test;

import java.io.File;

public class ImageUtilTest extends BaseTest{

    @Test
    public void generateThumbnailsTest(){
        File thumb = new File("/Users/willliu/Downloads/id_card_back.jpeg");
        String relativePathName = ImageUtil.generateThumbnail(thumb,"/upload/");
        System.out.println(relativePathName);
    }
}
