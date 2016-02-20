package com.waiter.server.services.common.model;

import com.waiter.server.services.name.model.Name;

import javax.persistence.*;

/**
 * @author shahenpoghosyan
 */
@MappedSuperclass
public abstract class AbstractNamedEntityModel extends AbstractEntityModel {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "name_id", nullable = false)
    private Name name;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractNamedEntityModel that = (AbstractNamedEntityModel) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AbstractNamedEntityModel{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
