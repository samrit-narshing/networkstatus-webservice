package com.project.rest.resources.network;

import com.project.core.models.entities.network.NodeAlert;
import com.project.core.models.entities.network.NodeAlertInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NodeAlertResource extends ResourceSupport {

    private Long nodeAlertID = 0L;

    private int type = 0;
    private String description = "";

    private List<NodeAlertInfoResource> nodeAlertInfoResources = new ArrayList<>();

    public Long getNodeAlertID() {
        return nodeAlertID;
    }

    public void setNodeAlertID(Long nodeAlertID) {
        this.nodeAlertID = nodeAlertID;
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

    public List<NodeAlertInfoResource> getNodeAlertInfoResources() {
        return nodeAlertInfoResources;
    }

    public void setNodeAlertInfoResources(List<NodeAlertInfoResource> nodeAlertInfoResources) {
        this.nodeAlertInfoResources = nodeAlertInfoResources;
    }

    public NodeAlert toNNodeAlert() {
        NodeAlert object = new NodeAlert();
        object.setId(getNodeAlertID());
        object.setType(getType());
        object.setDescription(getDescription());

        //StudentInClassGroupResource
        Set<NodeAlertInfo> nodeAlertInfos = new HashSet<>();
        if (getNodeAlertInfoResources() != null) {
            getNodeAlertInfoResources().stream().forEach((studentInClassGroupResource) -> {
                nodeAlertInfos.add(studentInClassGroupResource.toNNodeAlertInfo());
            });
        }
        object.setNodeAlertInfos(nodeAlertInfos);
        //

        return object;
    }
}
