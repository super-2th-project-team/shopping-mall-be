package com.be01.prj2.role;

public enum CartStatus {

    NOTPAY("ROLE_NOTPAY"),
    PAY("ROLE_PAY");

    private String name;
    CartStatus(String name) {
        this.name = name;
    }

    public String getName{
        return name;
    }
}
