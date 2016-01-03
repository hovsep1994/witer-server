package com.waiter.server.services.language.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.common.model.AbstractNamedEntityModel;

/**
 * @author shahenpoghosyan
 */
public class Language extends AbstractNamedEntityModel {
    private String code;
    private String name;
    private String nativeName;

    public Language(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
