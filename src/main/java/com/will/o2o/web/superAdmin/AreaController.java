package com.will.o2o.web.superAdmin;

import com.will.o2o.entity.Area;
import com.will.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superAdmin")
public class AreaController {
    Logger logger = LoggerFactory.getLogger(AreaController.class);

    private AreaService areaService;

    public AreaService getAreaService() {
        return areaService;
    }

    @Autowired
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }

    @RequestMapping(value = "/listArea", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listArea() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Area> list = new ArrayList<Area>();
        try {
            list = areaService.getAreaList();
            modelMap.put("rows", list);
            modelMap.put("total", list.size());
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            logger.error(e.getMessage());
            return modelMap;
        }

        return modelMap;
    }

}









