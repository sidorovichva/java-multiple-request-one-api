package com.vs.javamultiplerequestoneapi.services;

import com.vs.javamultiplerequestoneapi.enums.DP;
import com.vs.javamultiplerequestoneapi.models.requests.results.SingleTestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractService {

    private static final String folder = "files/results/";

    public void saveToCSV(List<SingleTestResult> list, DP dp) {
        String filename = "-" + LocalDateTime.now();

        File file = new File(folder + dp.toString() + filename);
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.write(list.get(0).csvHeader() + "\n");
            list.forEach(e -> pw.write(e.toCsvRow() + "\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
