package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-3 下午8:24
 * Summary:
 */
@Controller
@RequestMapping
public class TimelineController {


    @Autowired
    private ProductService productService;

    @RequestMapping("/{product}/timeline")
    public String timeLine(@PathVariable("product") String code, Model model){
        Product product = productService.getTimeLine(code);
        model.addAttribute("product", product);
        return "timeline";
    }

}
