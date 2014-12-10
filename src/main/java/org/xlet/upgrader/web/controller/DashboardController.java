package org.xlet.upgrader.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Creator: JimmyLin
 * DateTime: 14-10-8 上午9:47
 * Summary:
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController {

    @RequestMapping(value = "products")
    public String products() {
        return "dashboard/products";
    }

    @RequestMapping(value = "versions")
    public String versions() {
        return "dashboard/versions";
    }

    @RequestMapping(value="logs")
    public String changeLogs(){
        return "dashboard/logs";
    }

    @RequestMapping(value = "feedback")
    public String feedback(){
        return "dashboard/feedback";
    }

    @RequestMapping(value = "filelist")
    public String fileList(){
        return "dashboard/filelist";
    }
}
