package com.project.core.models.entities.network;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 *
 * @author Samrit
 */
@Entity
public class NodeAlert {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type")
    private int type;

    @Lob
    @Column(name = "description", length = 2000)
    private String description;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "nodealert_id")
    private Set<NodeAlertInfo> nodeAlertInfos = new HashSet<>();

    public Set<NodeAlertInfo> getNodeAlertInfos() {
        return nodeAlertInfos;
    }

    public void setNodeAlertInfos(Set<NodeAlertInfo> nodeAlertInfos) {
        this.nodeAlertInfos = nodeAlertInfos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
