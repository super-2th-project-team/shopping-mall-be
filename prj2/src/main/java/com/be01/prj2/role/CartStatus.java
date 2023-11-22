package com.be01.prj2.role;

public enum CartStatus {

    ROLE_NOTPAY("ROLE_NOTPAY"),
    NOTPAY("NOTPAY"),
    PAY("PAY");

    private String status;

    CartStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return status;
    }

    public static CartStatus fromString(String status) {
        for (CartStatus cs : CartStatus.values()) {
            if (cs.status.equalsIgnoreCase(status)) {
                return cs;
            }
        }
        throw new IllegalArgumentException("No enum constant " + CartStatus.class.getCanonicalName() + "." + status);
    }
}
