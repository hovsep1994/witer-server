package com.waiter.server.services.common.model;

import com.waiter.server.services.name.model.NameTranslation;

import javax.persistence.*;

/**
 * @author shahenpoghosyan
 */
@MappedSuperclass
public abstract class AbstractNamedEntityModel extends AbstractEntityModel {

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "name_id", nullable = false)
    private NameTranslation nameTranslation;

    public NameTranslation getNameTranslation() {
        return nameTranslation;
    }

    public void setNameTranslation(NameTranslation nameTranslation) {
        this.nameTranslation = nameTranslation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractNamedEntityModel that = (AbstractNamedEntityModel) o;

        return !(nameTranslation != null ? !nameTranslation.equals(that.nameTranslation) : that.nameTranslation != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nameTranslation != null ? nameTranslation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AbstractNamedEntityModel{" +
                "nameTranslation='" + nameTranslation + '\'' +
                "} " + super.toString();
    }
}
