package com.vs.javamultiplerequestoneapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vs.javamultiplerequestoneapi.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Fetcher {

    private final ObjectMapper objectMapper;

    //private final static String link = "http://prod-setooproposal.isetoo.com:8098/test/proposal/0922a999-8e09-47dc-b7d3-5f6bc38ff032";
    private final static String link = "http://qa-setoopricing.isetoo.com:8099/ferriesArrivalDelay";
    //private final String link = "http://localhost:8081/persons";

    private static Map<String, Double> map;

    public PricingResponse getPerson() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String message = objectMapper.writeValueAsString(prepareObject());
        System.out.println(message);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(message))
                .header("Content-type", "application/json")
                .header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpbnZpYUBpbnZpYS5kZSIsImp0aSI6IjkyODVjZTY5LTY0MjktNDI2Yy05YTNhLWJhNzQ3ZjM5OGI2ZiIsInJvbGVzIjoiW1wiQUNDRVBUXCIsXCJBR0VOVF9UT09MXCIsXCJBUFBST1ZFX1BJUFwiLFwiQ0FOQ0VMX0RQXCIsXCJDQU5DRUxfUFJPVEVDVElPTlwiLFwiQ0hBUkdFX0FORF9BQ0NFUFRcIixcIkNMT1NFX0NMQUlNXCIsXCJDUkVBVEVfVVNFUlwiLFwiREVMRVRFX0NBUFNcIixcIkRFTEVURV9VU0VSXCIsXCJEVU1NWV9QUklWSUxFR0VcIixcIkVESVRfQkxBQ0tMSVNUXCIsXCJFRElUX0NBUFNcIixcIkVESVRfRElTVFJJQlVUT1JfU0VUVElOR1NcIixcIkVESVRfUElQXCIsXCJFRElUX1BPTElDWVwiLFwiRURJVF9SRUZfREFUQVwiLFwiRURJVF9ST0xFU19QUklWSUxFR0VTXCIsXCJFRElUX1NZU1RFTV9NT0RFXCIsXCJFTElHSUJJTElUWV9RVUVSWV9QQUdFXCIsXCJFWFBPUlRfRElTVFJJQlVUT1JTXCIsXCJFWFBPUlRfUElQXCIsXCJHRVRfUFJPUE9TQUxcIixcIkdSQVBISUNTXCIsXCJJTVBFUlNPTkFURVwiLFwiSU1QT1JUX1BJUFwiLFwiSU5JVF9XSVRIRFJBV0FMXCIsXCJJU19FTElHQUJMRVwiLFwiTUFOVUFMX1RSSUdHRVJcIixcIlBBVVNFX1BJUFwiLFwiUExBWV9QSVBcIixcIlJFQURfVVNFUlwiLFwiUkVQUk9QT1NFXCIsXCJSRVNFTkRfTUFJTFwiLFwiUk9MRV9BRE1JTlwiLFwiUk9MRV9DT01QQU5ZQURNSU5cIixcIlJPTEVfQ09NUEFOWUFETUlOX0JFVEFcIixcIlJPTEVfQ09NUEFOWVVTRVJcIixcIlJPTEVfRElTVFJJQlVUT1JfQVBJXCIsXCJST0xFX0lOU1VSRVJfQUdFTlRcIixcIlJPTEVfSU5URUdSQVRJT05fQURNSU5cIixcIlJPTEVfUklTS19DQVJSSUVSX0VESVRPUlwiLFwiUk9MRV9SSVNLX0NBUlJJRVJfVklFV0VSXCIsXCJST0xFX1NFVE9PX0FDQ09VTlRfTUFOQUdFUlwiLFwiUk9MRV9TRVRPT19JTlNVUkFOQ0VfQURNSU5cIixcIlJPTEVfU0VUT09fSU5TVVJBTkNFX1VTRVJcIixcIlJPTEVfU0tJX09SQUNMRVwiLFwiUk9MRV9TWVNBRE1JTlwiLFwiUk9MRV9URVNUX0VYUE9SVEVSXCIsXCJTRU5EX1BJUF9GT1JfQVBQUk9WQUxcIixcIlNLSV9PUkFDTEVcIixcIlNVU1BFTkRfUElQXCIsXCJVUERBVEVfVVNFUlwiLFwiVklFV19DQVBTX0RBU0hCT0FSRFwiLFwiVklFV19DT1ZFUlNcIixcIlZJRVdfREVTS1RPUF9SRVBPUlRTXCIsXCJWSUVXX0RJU1RSSUJVVE9SX1BJUFwiLFwiVklFV19ESVNUUklCVVRPUl9SRVBPUlRTXCIsXCJWSUVXX0ZJTkFOQ0VfUkVQT1JUU1wiLFwiVklFV19JTlNVUkVSX1JFUE9SVFNcIixcIlZJRVdfUFJPVEVDVElPTlNcIixcIlZJRVdfUFJPVklERVJfUkVQT1JUU1wiXSIsImRpc3RyaWJ1dG9yIjoiSW52aWEiLCJleHAiOjE2NTM0NzkzNTd9.U6NwghKGDL3JdQfVcM_fIM0c6qKeq1Cq3hDf9JmEfrml9DMBy4yFsdsgiR2NGcnXhNa5k-AqzvF-F1Sc4Pc3Jg")
                .uri(URI.create(link))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body()
                .replaceAll("^\\[", "")
                .replaceAll("]$", "");

        System.out.println(response.statusCode());
        System.out.println(responseBody);

        SparkSession sparkSession = SparkSession.builder().getOrCreate();

        return objectMapper.readValue(responseBody, PricingResponse.class);
    }

    private PricingFerryRequest prepareObject() {
        Disruption disruption = new Disruption(120., true);
        FerryTripEntry arrival = new FerryTripEntry(LocalDateTime.of(2022, 7, 2, 12, 35), "GRMLO");
        FerryTripEntry departure = new FerryTripEntry(LocalDateTime.of(2022, 7, 2, 9, 30), "GRPIR");
        PricingFerryItinerary itinerary = new PricingFerryItinerary(arrival, departure, "GRPIR|GRMLO", "SJT");
        return new PricingFerryRequest(List.of(disruption), List.of(itinerary));
    }
}
