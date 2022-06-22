package com.vs.javamultiplerequestoneapi.enums;

public enum SOURCE {

    ota("/flight/detailed"),
    pricing("http://localhost:8096/");

    private String link;

    SOURCE(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
