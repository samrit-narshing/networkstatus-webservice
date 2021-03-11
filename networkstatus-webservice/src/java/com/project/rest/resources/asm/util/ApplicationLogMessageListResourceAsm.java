package com.project.rest.resources.asm.util;

import com.project.core.services.util.ApplicationLogMessageList;
import com.project.rest.mvc.web.ApplicationLogMessageRESTController_ForWeb;
import com.project.rest.resources.util.ApplicationLogMessageListResource;
import com.project.rest.resources.util.ApplicationLogMessageResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 *
 * @author Samrit
 */
public class ApplicationLogMessageListResourceAsm extends ResourceAssemblerSupport<ApplicationLogMessageList, ApplicationLogMessageListResource> {

    public ApplicationLogMessageListResourceAsm() {
        super(ApplicationLogMessageRESTController_ForWeb.class, ApplicationLogMessageListResource.class);
    }

    @Override
    public ApplicationLogMessageListResource toResource(ApplicationLogMessageList userList) {
        List<ApplicationLogMessageResource> resList = new ApplicationLogMessageResourceAsm().toResources(userList.getApplicationLogMessages());
        ApplicationLogMessageListResource finalRes = new ApplicationLogMessageListResource();
        finalRes.setApplicationLogMessageResources(resList);
        finalRes.setTotalPages(userList.getTotalPages());
        finalRes.setTotalDocuments(userList.getTotalDocuments());
        return finalRes;
    }
}
