package com.klzan.core.lock;

public enum LockStack {
    MNGUSER_LOCK("/KARAZAM/P2P/MNGUSER/"),
    USER_LOCK("/KARAZAM/P2P/USER/"),
    INVESTMENT_LOCK("/KARAZAM/P2P/INVESTMENT/"),
    ORDER_NO_LOCK("/KARAZAM/P2P/ORDERNO/");

    private String key;

    LockStack(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}