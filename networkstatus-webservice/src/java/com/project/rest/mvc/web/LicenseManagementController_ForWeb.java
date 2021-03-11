/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.rest.mvc.web;

import license.LicenseManagement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Samrit
 */
@Controller
@RequestMapping(value = "/core/license")
public class LicenseManagementController_ForWeb {

    final private String failedMessage = "<br/>\n"
            + "<h2> License Failed. Need New License Key!.</h2><br>\n";

    final private String failedNewLicenseMessage = "<br/>\n"
            + "<h2> New License Is Expired Or Invalid. Need New Valid License Key!.</h2><br>\n";

    final private String emptyFilefailedMessage = "<br/>\n"
            + " <h2> No License File Found. Please Browse The Valid License Key!.</h2><br>\n";
    final private String usedFilefailedMessage = "<br/>\n"
            + " <h2> Already Used License File Found. Please Browse The New Valid License Key!.</h2><br>\n";

    final private String mainMessage = " <br/>\n"
            + " Please register with the new valid license file to use this copy of SimC-Logger.<br/>\n"
            + " Please Contact Triumph Secure Singapore Pte.Ltd for the process of registration with the MAC address and Host Id of this machine.<br/>\n"
            + " <br/>\n"
            + " <b> "
            + " <b> Mac Address : </b>"
            + LicenseManagement.getAnyInterfaceMacAddressExceptBridge()
            + " </b> "
            + " <br/>\n"
            + " <b> "
            + "Host Id : "
            + LicenseManagement.getHostId()
            + " </b> "
            + " <br/>\n"
            + " <br/>\n"
            + " Thank you.\n";

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView licenseConfigDisplay(final RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        try {
            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            boolean isExpired = LicenseManagement.isExpired(request);

            String expiredMessage = "<font color=\"red\">"
                    + " Expiry Date :" + LicenseManagement.getExpireyDate(request)
                    + " <br/>\n"
                    + " Expired :" + (isExpired == true ? "YES" : "NO")
                    + " <br/>\n";

            if (isExpired) {
                expiredMessage += failedMessage;
            }
            expiredMessage += mainMessage;
            expiredMessage += " </font>";

            model.addObject("pageMessage", expiredMessage);
            model.setViewName("license_config");
            response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            return model;
        } catch (Exception e) {
//            Log4jUtil.fatal(e);
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

    @RequestMapping(value = "/submitUploadLicenseKey", method = RequestMethod.POST)
    public ModelAndView uploadLicenseKey(@RequestParam("file") MultipartFile file, final RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        try {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security - Authenticated ");
            model.addObject("message", "Logged On!");
            if (!file.isEmpty()) {
                boolean isRegisterd = LicenseManagement.registerLicenseWithUploadedFile(file, request);
                boolean isExpired = LicenseManagement.isExpired(request);
                if (isRegisterd) {
                    model.addObject("pageMessage", "File '" + file.getOriginalFilename() + "' Uploaded Successfully." + " Exiprary Date :" + LicenseManagement.getExpireyDate(request) + " Expired :" + isExpired);

                    if (isExpired) {
                        String expiredMessage = "<font color=\"red\">"
                                + " Expiry Date :" + LicenseManagement.getExpireyDate(request)
                                + " <br/>\n"
                                + " Expired :" + (isExpired == true ? "YES" : "NO")
                                + " <br/>\n";

                        if (isExpired) {
                            expiredMessage += failedNewLicenseMessage;
                        }
                        expiredMessage += mainMessage;
                        expiredMessage
                                += " <br/>\n"
                                + " <br/>\n"
                                + " Process cannot be completed. The provided license is already Used, Expired or Invalid."
                                + " <br/>\n"
                                + " </font>";

                        model.addObject("pageError", expiredMessage);
                    }

                    model.setViewName("license_config");
                } else {
                    String expiredMessage = "<font color=\"red\">"
                            + " Expiry Date :" + LicenseManagement.getExpireyDate(request)
                            + " <br/>\n"
                            + " Expired :" + (isExpired == true ? "YES" : "NO")
                            + " <br/>\n";

                    if (isExpired) {
                        expiredMessage += failedMessage;
                    }
                    expiredMessage += usedFilefailedMessage;
                    expiredMessage += mainMessage;
                    expiredMessage
                            += " <br/>\n"
                            + " <br/>\n"
                            + " Process cannot be completed. The provided license is already Used, Expired or Invalid."
                            + " <br/>\n"
                            + " </font>";

                    model.addObject("pageError", expiredMessage);

                    model.setViewName("license_config");
                }
                return model;
            } else {
                String expiredMessage = "<font color=\"red\">"
                        + " Expiry Date :" + LicenseManagement.getExpireyDate(request)
                        + " <br/>\n";

                expiredMessage += emptyFilefailedMessage;
                expiredMessage += mainMessage;
                expiredMessage
                        += " <br/>\n"
                        + " <br/>\n"
                        + "Process cannot be completed. Invalid File Being Uploaded."
                        + " <br/>\n"
                        + " </font>";

                model.addObject("pageError", expiredMessage);
                model.setViewName("license_config");
            }

            return model;

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("pageError", "Error in page." + e.getMessage());
            return new ModelAndView("redirect:" + "/result");
        }
    }

}
