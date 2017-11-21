package com.will.o2o.dao;

import com.will.o2o.BaseTest;
import com.will.o2o.entity.Area;
import com.will.o2o.entity.Shop;
import com.will.o2o.entity.ShopCategory;
import com.will.o2o.entity.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;

import java.util.Date;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        UserInfo owner = new UserInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("711");
        shop.setShopDesc("饭超级难吃");
        shop.setPhone("666");
        shop.setShopImg("18禁");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("再这么难吃不让干了");

        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        UserInfo owner = new UserInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopId(1L);
        shop.setShopName("711");
        shop.setShopDesc("今天特价");
        shop.setAdvice("求你把饭做的好吃点");

        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }
}
