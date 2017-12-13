package com.will.o2o.dao;

import com.will.o2o.BaseTest;
import com.will.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategoryTest(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println(shopCategoryList.get(0).getShopCategoryName());
        System.out.println(shopCategoryList.get(1).getShopCategoryName());

    }

    @Test
    public void queryShopCategoryWithParentTest(){
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(2L);
        ShopCategory testShopCategory = new ShopCategory();
        testShopCategory.setParent(parent);
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(testShopCategory);
        System.out.println(shopCategoryList.get(0).getShopCategoryName());
        System.out.println(shopCategoryList.get(1).getShopCategoryName());

    }
}
