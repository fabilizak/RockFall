package com.inuwa.rockfall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HighScoreManager {

    private Map<String,Integer> highScores;
    private Gson gson;
    private String json;
    private Type mapType;
    private FileHandle file;
    private String rootPath;

    public HighScoreManager(){
        gson = new Gson();
        mapType = new TypeToken<Map<String,Integer>>(){}.getType();
        rootPath = Gdx.files.getLocalStoragePath();
        file = Gdx.files.local("scores.json");
        if (!file.exists()){
            try {
                file.file().createNewFile();
                highScores = new HashMap<>();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else
            if (file.length() == 0)
                highScores = new HashMap<>();
            else
                highScores = getHighScores();

    }

    public void updateHighScore(String playerName, int score){
        highScores.put(playerName, score);
        highScores = sortByScores(highScores);
        json = gson.toJson(highScores, mapType);
        file.writeString(json, false);
    }

    public Map getHighScores(){
        highScores = gson.fromJson(file.readString(), mapType);
        System.out.println(file.readString());
        return highScores;
    }

    public static <playerName, score extends Comparable<? super score>> Map<playerName, score> sortByScores(Map<playerName, score> map) {
        List<Map.Entry<playerName, score>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.<playerName,score>comparingByValue().reversed());

        if (list.size() > 10){
            list.remove(10);
        }

        Map<playerName, score> result = new LinkedHashMap<>();
        for (Map.Entry<playerName, score> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


}
