package com.kozak.testtask.processors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class MapThreadWriter extends Thread {
    private Map<String, Long> mapName;
    private String fileName;

    public MapThreadWriter(Map<String,Long> mapName, String fileName) {
        this.mapName = mapName;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            File directory = new File(System.getenv("DATA_FOLDER") + "/results");
            directory.mkdir();
            File file = new File(directory + "/" + fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, Long> entry : mapName.entrySet()) {
                bw.write(entry.getKey() + " " + entry.getValue());
                bw.newLine();
            }
            bw.close();
            System.out.println("Writing complete to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
