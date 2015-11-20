package com.waiter.server.commons.entities;

/**
 * @author shahenpoghosyan
 */
public class Language {
    private String code;
    private String name;
    private String nativeName;

    public Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Language setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Language setName(String name) {
        this.name = name;
        return this;
    }

    public String getNativeName() {
        return nativeName;
    }

    public Language setNativeName(String nativeName) {
        this.nativeName = nativeName;
        return this;
    }
}
