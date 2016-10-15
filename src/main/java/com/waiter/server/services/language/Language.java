package com.waiter.server.services.language;

/**
 * @author shahenpoghosyan
 */
public enum Language {

    en("english"), hy("armenian"), ru("russian"), nl("dutch");

    private String nativeName;

    Language(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNativeName() {
        return this.nativeName;
    }
}
