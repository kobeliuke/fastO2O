package com.will.o2o.service.impl;

import com.will.o2o.dao.ShopCategoryDao;
import com.will.o2o.entity.ShopCategory;
import com.will.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    private ShopCategoryDao shopCategoryDao;

    public ShopCategoryDao getShopCategoryDao() {
        return shopCategoryDao;
    }

    @Autowired
    public void setShopCategoryDao(ShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory condition) {
        return shopCategoryDao.queryShopCategory(condition);
    }
}
