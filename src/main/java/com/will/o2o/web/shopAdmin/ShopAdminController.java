package com.will.o2o.web.shopAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * routing forward
 */
@Controller
@RequestMapping(value = "shop", method={RequestMethod.GET})
public class ShopAdminController {

    @RequestMapping(value = "/shopOperation")
    public String shopOperation(){
        return "shop/shopOperation";
    }
}
