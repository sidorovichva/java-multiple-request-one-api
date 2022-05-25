package com.vs.javamultiplerequestoneapi;

public enum DP {

    Flight("http://qa-setoopricing.isetoo.com:8099/flightsOverallDelay"),
    Ferry("http://qa-setoopricing.isetoo.com:8099/ferriesArrivalDelay");

    private final String link;

    DP(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
