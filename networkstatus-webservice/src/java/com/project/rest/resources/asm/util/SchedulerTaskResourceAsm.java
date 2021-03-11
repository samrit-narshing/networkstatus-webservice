package com.project.rest.resources.asm.util;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import com.project.core.models.entities.util.SchedulerTask;
import com.project.rest.mvc.web.SchedulerRESTController_ForWeb;
import com.project.rest.resources.util.SchedulerTaskResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Samrit
 */
public class SchedulerTaskResourceAsm extends ResourceAssemblerSupport<SchedulerTask, SchedulerTaskResource> {

    public SchedulerTaskResourceAsm() {
        super(SchedulerRESTController_ForWeb.class, SchedulerTaskResource.class);
    }

    @Override
    public SchedulerTaskResource toResource(SchedulerTask object) {
        SchedulerTaskResource res = new SchedulerTaskResource();
        res.setName(object.getName());
        res.setActive(object.getActive());
        res.setTableId(object.getId());
        res.add(linkTo(methodOn(SchedulerRESTController_ForWeb.class).getScheduleTaskByID(object.getId())).withSelfRel());
        return res;
    }
}
