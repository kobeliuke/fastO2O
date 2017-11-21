package com.will.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static final Random random = new Random();
    private static final SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
    //TODO：use config file to configure this later
    private static String waterMarkPathName = Thread.currentThread().getContextClassLoader()
            .getResource("").getPath() + "watermark.png";

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return newFile;
    }


    /**
     * generate thumb to an absolute path
     *
     * @param thumbnail
     * @param targetPath
     * @return return relativePathName into the database
     */
    public static String generateThumbnail(File thumbnail, String targetPath) {
        String realFileName = getRandomFileName();
        String fileExtension = getFileExtension(thumbnail.getName());
        String relativePathName = targetPath + realFileName + fileExtension;
        logger.debug("current relativePathName: " + relativePathName);
        makeDirPath(PathUtil.getImgBasePath() + targetPath);
        logger.debug("current absolutePath: " + PathUtil.getImgBasePath() + relativePathName);
        File dest = new File(PathUtil.getImgBasePath() + relativePathName);
        try {
            logger.debug(waterMarkPathName);
            Thumbnails.of(thumbnail).size(400, 400)
                    .watermark(Positions.TOP_RIGHT, ImageIO.read(new File(waterMarkPathName)), 0.3f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("failed to create thumbnail.\n" + e.getMessage());
        }
        return relativePathName;
    }


    //fileName: yyyy-MM-dd-hh-mm-ss + xxxxx
    private static String getRandomFileName() {

        //[10000, 99999 )
        int randomNumber = random.nextInt(89999) + 10000;
        String nowTimeStr = simpleDate.format(new Date());
        return nowTimeStr + randomNumber;
    }

    //get .jpeg .png .tga and so on
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //create the path for img
    private static void makeDirPath(String absolutePath) {
        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
