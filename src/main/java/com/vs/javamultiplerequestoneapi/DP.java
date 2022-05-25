package com.vs.javamultiplerequestoneapi;

public enum DP {

    Flight(""),
    Ferry("");

    private final String link;

    DP(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
