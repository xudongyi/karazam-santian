/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.p2p.model.Area;
import com.klzan.p2p.service.content.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地区
 * @author: chenxinglin
 */
@Controller("webAreaController")
@RequestMapping("/area")
public class AreaController extends BaseController {

    @Inject
    private AreaService areaService;

    @RequestMapping(value = "jsons", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, String> jsons(Integer parentId) {
        List<Area> areas = new ArrayList<Area>();
        if (parentId != null) {
            areas = areaService.findChildren(parentId);
        } else {
            areas = areaService.findRoots();
        }
        Map<Integer, String> options = new HashMap<Integer, String>();
        for (Area area : areas) {
            options.put(area.getId(), area.getName());
        }
        return options;
    }

}





