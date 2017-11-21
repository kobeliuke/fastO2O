package com.will.o2o.service;

import com.will.o2o.dto.ShopExecution;
import com.will.o2o.entity.Shop;

import java.io.File;

public interface ShopService {
    ShopExecution addShop(Shop shop, File img);
}
