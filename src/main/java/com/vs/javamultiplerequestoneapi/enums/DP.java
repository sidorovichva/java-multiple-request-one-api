package com.vs.javamultiplerequestoneapi.enums;

import java.util.NoSuchElementException;

public enum DP {

    Flight(FlightEnvs.class),
    Ferry(FerryEnvs.class),
    Weather(WeatherEnvs.class);

    private Class<? extends IEnv> clazz;

    DP(Class<? extends IEnv> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends IEnv> getClazz() {
        return clazz;
    }

    public interface IEnv {
        String getEnvironment(String env);
    }

    public enum FlightEnvs implements IEnv {
        QA("/flightsOverallDelay"),
        CI("/flightsOverallDelay"),
        local("http://localhost:8096/flightsOverallDelay");

        private String link;

        FlightEnvs(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        @Override
        public String getEnvironment(String env) {
            if (env.equalsIgnoreCase(QA.toString()))
                return QA.getLink();
            else if (env.equalsIgnoreCase(CI.toString()))
                return CI.getLink();
            else if (env.equalsIgnoreCase(local.toString()))
                return local.getLink();
            else throw new NoSuchElementException("there is no environment with such name: " + env);
        }
    }

    public enum FerryEnvs implements IEnv {
        QA("/ferriesArrivalDelay"),
        CI("/ferriesArrivalDelay"),
        local("http://localhost:8096/ferriesArrivalDelay");

        private String link;

        FerryEnvs(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        @Override
        public String getEnvironment(String env) {
            if (env.equalsIgnoreCase(QA.toString()))
                return QA.getLink();
            else if (env.equalsIgnoreCase(CI.toString()))
                return CI.getLink();
            else if (env.equalsIgnoreCase(local.toString()))
                return local.getLink();
            else throw new NoSuchElementException("there is no environment with such name: " + env);
        }
    }

    public enum WeatherEnvs implements IEnv {
        QA("/"),
        CI("/"),
        local("http://localhost:8096/");

        private String link;

        WeatherEnvs(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        @Override
        public String getEnvironment(String env) {
            if (env.equalsIgnoreCase(QA.toString()))
                return QA.getLink();
            else if (env.equalsIgnoreCase(CI.toString()))
                return CI.getLink();
            else if (env.equalsIgnoreCase(local.toString()))
                return local.getLink();
            else throw new NoSuchElementException("there is no environment with such name: " + env);
        }
    }
}
