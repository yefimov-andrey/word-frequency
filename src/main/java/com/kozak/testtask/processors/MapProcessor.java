package com.kozak.testtask.processors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapProcessor {


    public void setIdMap(Map<Integer, Character> idMap) {

        for(int i = 0; i < 66000; i ++){
            if(i <= 64 || i >= 123 || (i >= 91 && i <= 96))
                idMap.put(i, '1');
            else if((i >= 97 && i <= 103) || (i >= 65 && i <= 71))
                idMap.put(i, 'a');
            else if((i >= 104 && i <= 110) || (i >= 72 && i <= 78))
                idMap.put(i, 'h');
            else if((i >= 111 && i <= 117) || (i >= 79 && i <= 85))
                idMap.put(i, 'o');
            else if((i >= 118 && i <= 122) || (i >= 85 && i <= 90))
                idMap.put(i, 'v');
        }
    }

    public Map<String, Long> sortMap(Map<String,Long> inputMap){
        return inputMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void putElementIntoMap(Map<String,Long> inputMap, String element){
        if (inputMap.containsKey(element)) {
            inputMap.put(element, inputMap.get(element) + 1);
        } else {
            inputMap.put(element, 1L);
        }
    }

    public void writeMap(Map<String,Long> inputMap, String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for (Map.Entry<String, Long> entry : inputMap.entrySet()) {
            bw.write(entry.getKey() + " " + entry.getValue());
            bw.newLine();
        }
        bw.close();
    }

    public CopyOnWriteArrayList<Map<String,Long>> dataFromFileToMapList(String fileName, CopyOnWriteArrayList<Map<String,Long>> inputMapArrayList) throws IOException {
        Map<Integer, Character> idMap = new HashMap<>();
        Map<String, Long> wordsMapAG = inputMapArrayList.get(0);
        Map<String, Long> wordsMapHN = inputMapArrayList.get(1);
        Map<String, Long> wordsMapOU = inputMapArrayList.get(2);
        Map<String, Long> wordsMapVZ = inputMapArrayList.get(3);
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String s2="";
        boolean first;
        char id = 'a';
        int ascii = 1;
        setIdMap(idMap);
        for(String line; (line = br.readLine()) != null; ) {
            for (String s1 : line.split(" ")) {
                //s1 = s1.toLowerCase(Locale.ROOT); //lowercase all words
                s2="";
                first = true;
                id = 'a';
                for(char ch: s1.toCharArray()){
                    ascii = ch;
                    if((ascii >= 97 && ascii <= 122) || (ascii >= 65 && ascii <= 90)) //only non-special characters are put in
                        s2+=ch;
                    if(first)
                        id = idMap.get(ascii); //define the word map where the word is put
                    first = false;
                }
                if(s2 == "") //'words' only consisting of special characters won't be put into any word map
                    break;
                switch (id) {
                    case ('a'):
                        putElementIntoMap(wordsMapAG, s2);
                        break;
                    case ('h'):
                        putElementIntoMap(wordsMapHN, s2);
                        break;
                    case ('o'):
                        putElementIntoMap(wordsMapOU, s2);
                        break;
                    case ('v'):
                        putElementIntoMap(wordsMapVZ, s2);
                        break;
                    default:
                        break;
                }
            }
        }
        return new CopyOnWriteArrayList<>(Arrays.asList(wordsMapAG, wordsMapHN, wordsMapOU, wordsMapVZ));
    }


    public Set<String> listFilesUsingFileWalk(String dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

}
