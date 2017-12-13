package com.will.o2o.service;

import com.will.o2o.BaseTest;
import com.will.o2o.dto.ShopExecution;
import com.will.o2o.entity.Area;
import com.will.o2o.entity.Shop;
import com.will.o2o.entity.ShopCategory;
import com.will.o2o.entity.UserInfo;
import com.will.o2o.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

import java.io.File;

public class ShopServiceTest extends BaseTest {

    @Autowired
    ShopService shopService;

    @Test
    public void addShopTest(){

        Shop shop = new Shop();
        UserInfo owner = new UserInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("桔色成人");
        shop.setShopDesc("你懂得");
        shop.setPhone("110-120-666666");
        shop.setAdvice("checking");
        File shopImg = new File("/Users/willliu/Downloads/pic.png");
        ShopExecution shopExecution = shopService.addShop(shop,shopImg);
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getState());
        System.out.println(shopExecution.getStateInfo());
    }

    @Test
    public void modifyShopTest(){
        Shop shop = new Shop();
        shop.setShopId(5L);
        shop.setShopDesc("双十一买娃娃松打气筒");
        File imgFile = new File("/Users/willliu/Downloads/id_card_front.jpeg");
        ShopExecution shopExecution = shopService.modifyShop(shop,imgFile);
        System.out.println(shopExecution.getShop().getShopDesc());
    }
    @Test
    public void simpleTest(){
        File imgFile = new File("/Users/willliu/Downloads/id_card_front.jpeg");
        System.out.println(imgFile.exists());
    }
    @Test
    public void testSimple(){
        for(int i = 0; i< 5000; i++){
            String s1 = "hello";
        }

        String str1  = new String("SEU") + new String("Calvin");
        System.out.println(str1 == "SEUCalvin");
        System.out.println(str1.intern()==str1);

    }
}
