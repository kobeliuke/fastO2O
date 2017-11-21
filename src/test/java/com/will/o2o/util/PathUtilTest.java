package com.will.o2o.util;

import com.will.o2o.BaseTest;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PathUtilTest extends BaseTest {

    @Test
    public void getImgBasePathTest() {

        System.out.println(PathUtil.getImgBasePath());
        System.out.println(File.separator);
        System.out.println(System.getProperty("file.separator"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
    }

    @Test
    public void thumbnailsFuncTest() throws IOException {
        //example to use watermark
        Thumbnails.of(new File("/Users/willliu/Downloads/id_card_front.jpeg")).size(800, 600)
                .watermark(Positions.TOP_RIGHT,
                        ImageIO.read(new File("/Users/willliu/Downloads/irdeto.png")), 0.45f)
                .outputQuality(0.8f).toFile("/Users/willliu/Downloads/id_card_watermark.jpg");
    }
}
