package com.project.rest.resources.network;

import com.project.core.models.entities.network.EdgeInNetworkGroup;
import com.project.core.models.entities.network.NetworkGroup;
import com.project.core.models.entities.network.NodeInNetworkGroup;
import com.project.core.models.entities.network.RoleInNetworkGroup;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Samrit
 */
public class NetworkGroupResource extends ResourceSupport {

    private Long networkGroupID = 0L;
    private String name = "";
    private String code = "";
    private String description = "";
    private Long entryDate = 0L;

    private Integer statusCode = 0;

    private Set<NodeInNetworkGroupResource> nodeInNetworkGroupResources = new HashSet<>();

    private Set<EdgeInNetworkGroupResource> edgeInNetworkGroupResources = new HashSet<>();

    private Set<RoleInNetworkGroupResource> roleInNetworkGroupResources = new HashSet<>();

    private String entryByUsername = "";
    private String entryByUserType = "";

    private String lastUpdateByUsername = "";
    private String lastUpdateByUserType = "";
    private Long lastModifiedUnixTime = 0L;

    private Boolean enabled = false;

    private String roles;

    private List<RoleInNetworkGroupResource> roleInNetworkGroupListResources = new ArrayList<>();

    public List<RoleInNetworkGroupResource> getRoleInNetworkGroupListResources() {
        return roleInNetworkGroupListResources;
    }

    public void setRoleInNetworkGroupListResources(List<RoleInNetworkGroupResource> roleInNetworkGroupListResources) {
        this.roleInNetworkGroupListResources = roleInNetworkGroupListResources;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getNetworkGroupID() {
        return networkGroupID;
    }

    public void setNetworkGroupID(Long networkGroupID) {
        this.networkGroupID = networkGroupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Long entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Set<NodeInNetworkGroupResource> getNodeInNetworkGroupResources() {
        return nodeInNetworkGroupResources;
    }

    public void setNodeInNetworkGroupResources(Set<NodeInNetworkGroupResource> nodeInNetworkGroupResources) {
        this.nodeInNetworkGroupResources = nodeInNetworkGroupResources;
    }

    public Set<EdgeInNetworkGroupResource> getEdgeInNetworkGroupResources() {
        return edgeInNetworkGroupResources;
    }

    public void setEdgeInNetworkGroupResources(Set<EdgeInNetworkGroupResource> edgeInNetworkGroupResources) {
        this.edgeInNetworkGroupResources = edgeInNetworkGroupResources;
    }

    public String getEntryByUsername() {
        return entryByUsername;
    }

    public void setEntryByUsername(String entryByUsername) {
        this.entryByUsername = entryByUsername;
    }

    public String getEntryByUserType() {
        return entryByUserType;
    }

    public void setEntryByUserType(String entryByUserType) {
        this.entryByUserType = entryByUserType;
    }

    public String getLastUpdateByUsername() {
        return lastUpdateByUsername;
    }

    public void setLastUpdateByUsername(String lastUpdateByUsername) {
        this.lastUpdateByUsername = lastUpdateByUsername;
    }

    public String getLastUpdateByUserType() {
        return lastUpdateByUserType;
    }

    public void setLastUpdateByUserType(String lastUpdateByUserType) {
        this.lastUpdateByUserType = lastUpdateByUserType;
    }

    public Long getLastModifiedUnixTime() {
        return lastModifiedUnixTime;
    }

    public void setLastModifiedUnixTime(Long lastModifiedUnixTime) {
        this.lastModifiedUnixTime = lastModifiedUnixTime;
    }

    public Set<RoleInNetworkGroupResource> getRoleInNetworkGroupResources() {
        return roleInNetworkGroupResources;
    }

    public void setRoleInNetworkGroupResources(Set<RoleInNetworkGroupResource> roleInNetworkGroupResources) {
        this.roleInNetworkGroupResources = roleInNetworkGroupResources;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public NetworkGroup toNetworkGroup() {
        NetworkGroup networkGroup = new NetworkGroup();
        networkGroup.setId(getNetworkGroupID());
        networkGroup.setDescription(getDescription());
        networkGroup.setEntryDate(getEntryDate());
        networkGroup.setName(getName());
        networkGroup.setStatusCode(getStatusCode());
        networkGroup.setCode(getCode());
        networkGroup.setRoles(getRoles());
//NodeInNetworkGroupResource
        Set<NodeInNetworkGroup> localNodeInNetworkGroups = new HashSet<>();
        if (getNodeInNetworkGroupResources() != null) {
            getNodeInNetworkGroupResources().stream().forEach((nodeInNetworkGroupResource) -> {
                localNodeInNetworkGroups.add(nodeInNetworkGroupResource.toNodeInNetworkGroup());
            });
        }
        networkGroup.setNodesInNetworkGroup(localNodeInNetworkGroups);

        //EdgeInNetworkGroupResource
        Set<EdgeInNetworkGroup> localEdgeInNetworkGroups = new HashSet<>();
        if (getEdgeInNetworkGroupResources() != null) {
            getEdgeInNetworkGroupResources().stream().forEach((edgeInNetworkGroupResource) -> {
                localEdgeInNetworkGroups.add(edgeInNetworkGroupResource.toEdgeInNetworkGroup());
            });
        }
        networkGroup.setEdgesInNetworkGroup(localEdgeInNetworkGroups);

        //RoleInNetworkGroupResource
        Set<RoleInNetworkGroup> localRoleInNetworkGroups = new HashSet<>();
        if (getRoleInNetworkGroupResources() != null) {
            getRoleInNetworkGroupResources().stream().forEach((roleInNetworkGroupResource) -> {
                localRoleInNetworkGroups.add(roleInNetworkGroupResource.toRoleInNetworkGroup());
            });
        }
        networkGroup.setRolesInNetworkGroup(localRoleInNetworkGroups);

        networkGroup.setEntryByUserType(getEntryByUserType());
        networkGroup.setEntryByUsername(getEntryByUsername());
        networkGroup.setLastUpdateByUserType(getLastUpdateByUserType());
        networkGroup.setLastUpdateByUsername(getLastUpdateByUsername());
        networkGroup.setLastModifiedUnixTime(getLastModifiedUnixTime());
        networkGroup.setEnabled(getEnabled());

        return networkGroup;
    }
}
