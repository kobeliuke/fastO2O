package com.will.o2o.service;

import com.will.o2o.dto.ShopExecution;
import com.will.o2o.entity.Shop;
import com.will.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    ShopExecution addShop(Shop shop, File img) throws ShopOperationException;

    ShopExecution modifyShop(Shop shop, File shopImgFile) throws ShopOperationException;

    Shop getShopById(long shopId);

}
