package com.will.o2o.dao;

import com.will.o2o.entity.Area;

import java.util.List;
public interface AreaDao {
    /**
     * list the area
     * @return
     */
    List<Area> queryArea();
}
