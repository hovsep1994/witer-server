package com.waiter.server.services.common.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author shahenpoghosyan
 */
@MappedSuperclass
public abstract class AbstractEntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "updated", nullable = false)
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntityModel that = (AbstractEntityModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Entitiy{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
