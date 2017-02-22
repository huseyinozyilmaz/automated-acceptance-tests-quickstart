package org.huseyin.bdd;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ReportMerger {

    private static final Logger LOGGER = Logger.getLogger(ReportMerger.class.getName());
    private static ReportMerger instance;
    private File reportDirectory;

    private ReportMerger(){}

    public static synchronized ReportMerger getInstance(){
        if(instance == null){
            instance = new ReportMerger();
        }
        return instance;
    }

    public void merge(String reportDirectoryPath) {
        this.reportDirectory = new File(reportDirectoryPath);

        if (!reportDirectory.exists()) {
            LOGGER.severe("Cannot find report directory at " + reportDirectory.getAbsolutePath());
            return;
        }

        File[] files = getPartialJsonFiles();

        if (files.length == 0) {
            LOGGER.warning("Cannot find any json file in " + reportDirectory.getAbsolutePath());
        } else {
            LOGGER.info("Merging json reports in " + reportDirectory.getAbsolutePath());

            writeCombinedJson(combineJsonFiles(files));
            deletePartialFiles(files);
        }
    }

    private JSONArray combineJsonFiles(File[] files) {
        JSONParser parser = new JSONParser();
        JSONArray result = new JSONArray();
        Object object;
        JSONArray results;
        Reader reader;
        for (File json : files) {
            try {
                reader = new FileReader(json);
                object = parser.parse(reader);
                results = (JSONArray) object;
                result.addAll(results);
                reader.close();
            } catch (IOException e) {
                LOGGER.severe("Cannot read file: " + e.getMessage());
            } catch (ParseException e) {
                LOGGER.severe("Cannot parse file: " + e.getMessage());
            }
        }
        return result;
    }

    private void writeCombinedJson(JSONArray json) {
        final String filename = Paths.get(reportDirectory.getAbsolutePath(), "all.json").toString();
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toJSONString());
            file.flush();
            LOGGER.info("All json files are combined in to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File[] getPartialJsonFiles() {
        final FilenameFilter jsonFilter = (File file, String name) -> name.endsWith(".json");

        return reportDirectory.listFiles(jsonFilter);
    }

    private void deletePartialFiles(File[] filesToBeDeleted) {
        for (File file : filesToBeDeleted) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
