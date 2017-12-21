package com.will.o2o.web.shopAdmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.o2o.dto.ShopExecution;
import com.will.o2o.entity.Area;
import com.will.o2o.entity.Shop;
import com.will.o2o.entity.ShopCategory;
import com.will.o2o.entity.UserInfo;
import com.will.o2o.enums.ShopStateEnum;
import com.will.o2o.exceptions.ShopOperationException;
import com.will.o2o.service.AreaService;
import com.will.o2o.service.ShopCategoryService;
import com.will.o2o.service.ShopService;
import com.will.o2o.util.CodeUtil;
import com.will.o2o.util.HttpServletRequestUtil;
import com.will.o2o.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopAdmin")
public class ShopManagementController {

    private ShopService shopService;
    private ShopCategoryService shopCategoryService;
    private AreaService areaService;


    @RequestMapping(value = "/getshopManagementInfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId <= 0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj == null){
                modelMap.put("redirect", true);
                modelMap.put("url","/o2o/shopAdmin/shopList");
            }else {
                Shop currentShop = (Shop)currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("shopId", currentShop.getShopId());
            modelMap.put("redirect", false);
        }

        return modelMap;
    }
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        UserInfo user = new UserInfo();
        user.setUserId(1L);
        user.setName("洗剪吹");
        request.getSession().setAttribute("user", user);

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(userInfo);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShopList());
            // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
            request.getSession().setAttribute("shopList", se.getShopList());
            modelMap.put("success", true);
            modelMap.put("user", userInfo);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @RequestMapping(value = "/getShopInitInfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();

        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyShop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //0.check verification code
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        // 1.接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        // 2.修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se = null;
            try {
                if (shopImg == null) {
                    se = shopService.modifyShop(shop, null);
                } else {
                    se = shopService.modifyShop(shop, ImageUtil.transferCommonsMultipartFileToFile(shopImg));
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺ID");
            return modelMap;
        }
    }

    @RequestMapping(value = "/registerShop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //0.check verification code
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        //1.get the shop value
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2.register shop
        if (shop != null && shopImg != null) {
            UserInfo owner = (UserInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            //invoke service layer
            ShopExecution se = shopService.addShop(shop, ImageUtil.transferCommonsMultipartFileToFile(shopImg));
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请完善店铺信息或者重新上传图片");
            return modelMap;
        }
        //3.return result

    }

    @RequestMapping(value = "/getShopById", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getShopById(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }


    public ShopService getShopService() {
        return shopService;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }

    public ShopCategoryService getShopCategoryService() {
        return shopCategoryService;
    }

    @Autowired
    public void setShopCategoryService(ShopCategoryService shopCategoryService) {
        this.shopCategoryService = shopCategoryService;
    }

    public AreaService getAreaService() {
        return areaService;
    }

    @Autowired
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }
}
