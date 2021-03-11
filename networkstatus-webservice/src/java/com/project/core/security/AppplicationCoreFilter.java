/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.security;

/**
 *
 * @author Samrit
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import license.LicenseManagement;

// Implements Filter class
public class AppplicationCoreFilter implements Filter {

    String projectRootPath = "";

    public void init(FilterConfig config)
            throws ServletException {
        // Get init parameter 
        projectRootPath = config.getServletContext().getInitParameter("PROJECT_ROOT_PATH");

        //Print the init parameter 
        System.out.println("Test Param: >>>>>>>>>>>>>>>>>>>>>>>>" + projectRootPath);
    }

    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws java.io.IOException, ServletException {

//        // Get the IP address of client machine.   
//        String ipAddress = request.getRemoteAddr();
//
//        // Log the IP address and current timestamp.
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>IP " + ipAddress + ", Time "
//                + new Date().toString());
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        boolean isDebug = false;
        if (isDebug) {
            chain.doFilter(request, response);
        } else {
            boolean isExpired = LicenseManagement.isExpired(req);
            if (isExpired) {
                res.sendRedirect(projectRootPath + "/core/license/");
                return;
            } else {
                // Pass request back down the filter chain
                chain.doFilter(request, response);
            }
        }

    }

    public void destroy() {
        /* Called before the Filter instance is removed 
      from service by the web container*/
    }
}
