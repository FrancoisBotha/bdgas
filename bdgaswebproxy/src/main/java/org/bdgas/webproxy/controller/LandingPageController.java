package org.bdgas.webproxy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/")
public class LandingPageController {

    @Value("${endpoint.adminserver.domain}")
    private String protocol;

    @Value("${active.profile}")
    private String act;



    @GetMapping
    public String ShowLandingPage(ModelMap model) {

        log.info("In ShowLandingPage Controller");
        log.info(act);
        log.info(protocol);
        return "landingpage";


    }
}
