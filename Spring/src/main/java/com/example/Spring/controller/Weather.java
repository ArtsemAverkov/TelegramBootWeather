package com.example.Spring.controller;

import com.example.Spring.model.Model;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.Scanner;

@Component
@Slf4j
public class Weather {
    @Autowired
    WeatherIcons weatherIcons;

    public String getWeather(String message, Model model ) throws IOException {
        URL url = new URL
                ("https://api.openweathermap.org/data/2.5/weather?q="
                        +message+"&units=metric&appid=31652981b71a757e8a02b33568c8b4d6");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()){
            result += scanner.nextLine();
        }

        log.info("result = " + result);

        JSONObject jsonObject = new JSONObject(result);
        model.setName(jsonObject.getString("name"));

        JSONObject main = jsonObject.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray weather = jsonObject.getJSONArray("weather");
        for (int i = 0; i <weather.length() ; i++) {
            JSONObject jsonObjects = weather.getJSONObject(i);
        model.setMain((String) jsonObjects.get("main"));

        }
        String setIcons = weatherIcons.setIcons(model.getMain());
        String icons = EmojiParser.parseToUnicode(setIcons);
        log.info(icons);

        return "City: " + model.getName() + " " +
                icons + "\n" +
                "Temp: "+ model.getTemp() + "\n" +
                "Humidity: " + model.getHumidity();
    }
}


