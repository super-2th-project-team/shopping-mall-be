package com.be01.prj2.role;

public enum Role {
    USER("ROLE_USER"),

    SELLER("ROLE_SELLER"),

    SIGNOUT("ROLE_SIGNOUT");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
