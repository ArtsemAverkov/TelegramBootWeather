package com.example.Spring.controller;

import com.example.Spring.model.Model;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.Scanner;


//31652981b71a757e8a02b33568c8b4d6
@Component
@Slf4j
public class Weather {
    public String getWeather(String message, Model model ) throws IOException {
        URL url = new URL
                ("https://api.openweathermap.org/data/2.5/weather?q="+message+"&units=metric&appid=31652981b71a757e8a02b33568c8b4d6");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()){
            result += scanner.nextLine();
        }

        System.out.println("result = " + result);

        JSONObject jsonObject = new JSONObject(result);
        model.setName(jsonObject.getString("name"));

        JSONObject main = jsonObject.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray weather = jsonObject.getJSONArray("weather");
        for (int i = 0; i <weather.length() ; i++) {
            JSONObject jsonObjects = weather.getJSONObject(i);

        model.setIcon((String) jsonObjects.get("icon"));
        model.setMain((String) jsonObjects.get("main"));
    }

        return "City: " + model.getName() + "\n" +
                "Temp: "+ model.getTemp() + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}


