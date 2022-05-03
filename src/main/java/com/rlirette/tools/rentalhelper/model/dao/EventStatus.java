package com.rlirette.tools.rentalhelper.model.dao;

public enum EventStatus {
    TO_CREATE ("Cr&eacute;&eacute;"),
    TO_DELETE ("Annul&eacute;"),
    TO_MODIFY ("Modifi&eacute;"),
    UNCHANGED ("");

    private String value;

    EventStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
