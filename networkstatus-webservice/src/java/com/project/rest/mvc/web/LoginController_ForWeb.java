/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Samrit
 */
@Controller
public class LoginController_ForWeb {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("pageError", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("pageMessage", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    //for 403 access denied page
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
//        ModelAndView model = new ModelAndView();
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        model.setViewName("/login?logout");
        return "redirect:/login?logout";

    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());

            }

            model.setViewName("403");
        }
        return model;

    }

    //for 404 page not found 
    @RequestMapping(value = "/404", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView pageNotFound() {

        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());

            }
            model.setViewName("404");
        }
        return model;

    }

    //for 500 Internal Error 
    @RequestMapping(value = "/500", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView internalError() {

        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());
            }
            model.setViewName("500");
        }
        return model;

    }

    //for 500 MaxFileSizeUploaded 
    @RequestMapping(value = "/500_uploadSizeError", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView fileSizeLimitUploaded() {

        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //check if user is null or session is time out
        if (auth == null) {
            model.setViewName("/login?logout");
        } else {
            //check if user is login
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);

                model.addObject("username", userDetail.getUsername());

            }
            model.setViewName("500_uploadSizeError");
        }
        return model;

    }

}
