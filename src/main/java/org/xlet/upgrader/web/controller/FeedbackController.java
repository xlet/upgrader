package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.domain.Feedback;
import org.xlet.upgrader.service.FeedbackService;
import org.xlet.upgrader.util.IpUtils;
import org.xlet.upgrader.vo.Response;
import org.xlet.upgrader.vo.dashboard.FeedbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-10-9 下午4:39
 * Summary: 客户端反馈
 */
@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping
    public Response report(Feedback feedback, HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        LOG.debug("feedback from [{}]", ip);
        feedback.setIp(ip);
        feedbackService.save(feedback);
        return new Response().success();
    }


    @RequestMapping(value = "list")
    public List<FeedbackDTO> list(){
        return feedbackService.listAll();
    }

}
