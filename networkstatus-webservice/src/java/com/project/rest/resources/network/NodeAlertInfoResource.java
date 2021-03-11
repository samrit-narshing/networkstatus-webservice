package com.project.rest.resources.network;

import com.project.core.models.entities.network.NodeAlertInfo;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NodeAlertInfoResource extends ResourceSupport {

    private Long nodeAlertInfoID = 0L;

    private int type = 0;
    private String description = "";

    public Long getNodeAlertInfoID() {
        return nodeAlertInfoID;
    }

    public void setNodeAlertInfoID(Long nodeAlertInfoID) {
        this.nodeAlertInfoID = nodeAlertInfoID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NodeAlertInfo toNNodeAlertInfo() {
        NodeAlertInfo object = new NodeAlertInfo();
        object.setId(getNodeAlertInfoID());
        object.setType(getType());
        object.setDescription(getDescription());
        return object;
    }
}
