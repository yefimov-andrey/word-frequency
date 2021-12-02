package com.kozak.testtask.services;

import com.kozak.testtask.processors.MapProcessor;
import com.kozak.testtask.processors.MapThreadWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainService {

    private static final MapProcessor mapProcessor = new MapProcessor();

    public void analyze() throws IOException {
        String directory1 = System.getenv("DATA_FOLDER");
        Set<String> fileSet = mapProcessor.listFilesUsingFileWalk(directory1, 1);
        Map<String, Long> wordsMapAG = new HashMap<>();
        Map<String, Long> wordsMapHN = new HashMap<>();
        Map<String, Long> wordsMapOU = new HashMap<>();
        Map<String, Long> wordsMapVZ = new HashMap<>();
        CopyOnWriteArrayList<Map<String,Long>> mapArrayList = new CopyOnWriteArrayList<>(Arrays.asList(wordsMapAG, wordsMapHN, wordsMapOU, wordsMapVZ));
        for(String file: fileSet){
            System.out.println("Reading file " + file);
            mapArrayList = mapProcessor.dataFromFileToMapList((directory1 + "/" + file), mapArrayList);
        }
        Map<String,Long> sortedMap;
        for (Map<String,Long> map: mapArrayList) {
            sortedMap = mapProcessor.sortMap(map);
            mapArrayList.remove(map);
            mapArrayList.add(sortedMap);
        }
        Thread threadAG = new MapThreadWriter(mapArrayList.get(0),"resultsAG.txt");
        Thread threadHN = new MapThreadWriter(mapArrayList.get(1),"resultsHN.txt");
        Thread threadOU = new MapThreadWriter(mapArrayList.get(2),"resultsOU.txt");
        Thread threadVZ = new MapThreadWriter(mapArrayList.get(3),"resultsVZ.txt");
        threadAG.start();
        threadHN.start();
        threadOU.start();
        threadVZ.start();
    }

}
