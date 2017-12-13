package com.will.o2o.service.impl;

import com.will.o2o.dao.ShopDao;
import com.will.o2o.dto.ShopExecution;
import com.will.o2o.entity.Shop;
import com.will.o2o.enums.ShopStateEnum;
import com.will.o2o.exceptions.ShopOperationException;
import com.will.o2o.service.ShopService;
import com.will.o2o.util.ImageUtil;
import com.will.o2o.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {
    Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg) throws ShopOperationException {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                logger.error("failed to create a new shop");
                throw new ShopOperationException("failed to create a new shop");
            } else {
                if (shopImg != null) {
                    try {
                        addShopImg(shop, shopImg);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error: " + e.getMessage());
                    }
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        logger.error("failed to update img address");
                        throw new ShopOperationException("failed to update img address");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("add shop error" + e.getMessage());
            throw new ShopOperationException("addShop error: " + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);

    }

    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, File shopImgFile) throws ShopOperationException {
        try {
            if (shop == null || shop.getShopId() == null) {
                return new ShopExecution(ShopStateEnum.NULL_SHOP);
            }
            //1.have new picture?
            if (shopImgFile != null) {
                Shop oldShop = shopDao.queryByShopId(shop.getShopId());
                if (oldShop.getShopImg() != null) {
                    ImageUtil.deleteFileOrPath(oldShop.getShopImg());
                }
                addShopImg(shop, shopImgFile);

            }

            //2.update shop info
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.updateShop(shop);
            if (effectedNum <= 0) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryByShopId(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS, shop);
            }
        } catch (Exception e) {
            throw new ShopOperationException("modifyShop error:" + e.getMessage());
        }
    }

    @Override
    public Shop getShopById(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    private void addShopImg(Shop shop, File shopImg) {
        //get the img relative path  .../img/upload/
        String imgPath = PathUtil.getShopRelativeImgPath(shop.getShopId());
        //get the img position .../img/upload/
        String imgPosition = ImageUtil.generateThumbnail(shopImg, imgPath);
        shop.setShopImg(imgPosition);

    }
}
