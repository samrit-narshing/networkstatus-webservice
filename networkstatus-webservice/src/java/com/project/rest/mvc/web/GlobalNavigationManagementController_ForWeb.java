package com.project.rest.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class GlobalNavigationManagementController_ForWeb {

    @RequestMapping(value = "/firebase", method = RequestMethod.GET)
    public ModelAndView homePage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security - Authenticated ");
        model.addObject("message", "Logged On!");
        model.setViewName("firebase");
        return model;

    }

}
