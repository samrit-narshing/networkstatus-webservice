package com.project.rest.resources.asm.util;

import com.project.core.models.entities.util.ApplicationLogMessage;
import com.project.rest.mvc.web.ApplicationLogMessageRESTController_ForWeb;
import com.project.rest.resources.util.ApplicationLogMessageResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class ApplicationLogMessageResourceAsm extends ResourceAssemblerSupport<ApplicationLogMessage, ApplicationLogMessageResource> {

    public ApplicationLogMessageResourceAsm() {
        super(ApplicationLogMessageRESTController_ForWeb.class, ApplicationLogMessageResource.class);
    }

    @Override
    public ApplicationLogMessageResource toResource(ApplicationLogMessage object) {
        ApplicationLogMessageResource res = new ApplicationLogMessageResource();
        res.setEntryDate(object.getEntryDate());
        res.setSenderUsername(object.getSenderUsername());
        res.setLoggedUsername(object.getLoggedUsername());
        res.setDeviceType(object.getDeviceType());
        res.setDeviceModelName(object.getDeviceModelName());
        res.setLogMessageBlop(object.getLogMessageBlop());
//        res.setLogMessage(new String(object.getLogMessageBlop()));
        if (object.getLogMessageBlop() != null) {
            res.setLogMessage(new String(object.getLogMessageBlop()));
        } else {
            res.setLogMessage("");
        }
        res.setTableId(object.getId());
        res.add(linkTo(methodOn(ApplicationLogMessageRESTController_ForWeb.class).getApplicationLogMessageByID(object.getId())).withSelfRel());
        res.setAdminUsername(object.getAdminUsername());
        res.setAdminOperatorUsername(object.getAdminOperatorUsername());
        res.setAdminUserType(object.getAdminUserType());
        res.setAdminOperatorUserType(object.getAdminOperatorUserType());
        return res;
    }
}
