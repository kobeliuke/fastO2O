package com.will.o2o.service.impl;

import com.will.o2o.dto.ShopExecution;
import com.will.o2o.entity.Shop;
import com.will.o2o.enums.ShopStateEnum;
import com.will.o2o.service.ShopService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
public class ShopServiceImpl implements ShopService {
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, File img) {
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        if(shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        }


        try{

        }catch (Exception e){
            throw new RuntimeException("addShop error: " + e.getMessage());
        }

        return null;

    }
}
