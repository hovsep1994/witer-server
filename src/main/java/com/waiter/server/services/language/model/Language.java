package com.waiter.server.services.language.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.common.model.AbstractNamedEntityModel;

import javax.persistence.*;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "language")
public class Language extends AbstractEntityModel {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "nativeName")
    private String nativeName;

    public Language() {
    }

    public Language(String name) {
        setName(name);
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

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
