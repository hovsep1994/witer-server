package com.waiter.server.services.location.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "country")
public class Country extends AbstractEntityModel{

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "currency")
    private String currency;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (name != null ? !name.equals(country.name) : country.name != null) return false;
        if (code != null ? !code.equals(country.code) : country.code != null) return false;
        return !(currency != null ? !currency.equals(country.currency) : country.currency != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
