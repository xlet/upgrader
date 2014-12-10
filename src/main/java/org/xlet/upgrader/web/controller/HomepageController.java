package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.service.VersionService;
import org.xlet.upgrader.vo.homepage.DownloadVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-23 上午10:29
 * Summary:
 */
@Controller
@RequestMapping("/home")
public class HomepageController {
    private static final Logger LOG = LoggerFactory.getLogger(HomepageController.class);

    @Autowired
    private VersionService versionService;

    @RequestMapping(value = "/{product}")
    public ModelAndView toHomepage(@PathVariable("product") String product,UriComponentsBuilder builder) {
        DownloadVo vo = versionService.getDownloadInfo(builder.build().toUriString(), product);
        LOG.debug(vo.toString());
        return new ModelAndView("homepages/" + product, "product", vo);
    }

    @RequestMapping(value = "download/{product}")
    public String toDownload(@PathVariable("product") String product, UriComponentsBuilder builder) {
        DownloadVo vo = versionService.getDownloadInfo(builder.build().toUriString(), product);
        return "redirect:" + vo.getUrl();
    }
}
