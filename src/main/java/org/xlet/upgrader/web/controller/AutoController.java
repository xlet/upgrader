package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.service.UpdateService;
import org.xlet.upgrader.vo.dashboard.Response;
import org.xlet.upgrader.vo.dashboard.form.AutoSubmitForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-15 下午5:04
 * Summary:
 */
@RestController
@RequestMapping("/api/v1/submit")
public class AutoController {

    @Autowired
    private UpdateService updateService;

    @RequestMapping
    public Response submit(AutoSubmitForm form){
        updateService.genVersion(form);
        return new Response();
    }

}
