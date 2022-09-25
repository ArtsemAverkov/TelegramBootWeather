package com.example.Spring.controller;

import com.example.Spring.constants.WeatherSmile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherIcons {
    public  String setIcons(String s){
        switch (s){
            case "Thunderstorm":
                return WeatherSmile.CLOUD_WITH_LIGHTNING;

            case "Shower Rain":
                return  WeatherSmile.CLOUD_WITH_RAIN;

            case "Scattered Clouds":
            case "Clouds":
                return  WeatherSmile.CLOUD;

            case "Clear Sky":
                return  WeatherSmile.SUN;

            case "Few Clouds":
                return  WeatherSmile.SUN_BEHIND_CLOUD;

            case "Few Clouds?":
                return  WeatherSmile.SUN_BEHIND_LARGE_CLOUD;

            case "Few Clouds??":
                return  WeatherSmile.SUN_BEHIND_SMALL_CLOUD;

            case "Rain":
                return  WeatherSmile.SUN_BEHIND_RAIN_CLOUD;
            case "Snow":
                return  WeatherSmile.SNOW;
            default:
                log.info("Icon assignment error");
        }

        return s;
    }
}







