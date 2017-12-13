package com.will.o2o.service;

import com.will.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    List<ShopCategory>  getShopCategoryList(ShopCategory condition);
}
