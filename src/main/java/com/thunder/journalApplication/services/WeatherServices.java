package com.thunder.journalApplication.services;

import com.thunder.journalApplication.api.response.WeatherResponse;
import com.thunder.journalApplication.cache.AppCache;
import com.thunder.journalApplication.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServices {
    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_"+city,  WeatherResponse.class);
        if (weatherResponse != null){
            return weatherResponse;
        } else {
            String finalAPI = appCache.APP_CACHE.get("WEATHER_API")
                    .replace(Placeholders.CITY, city)
                    .replace(Placeholders.API_KEY, apiKey);

            ResponseEntity<WeatherResponse> response =
                    restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_"+city, body, 5);
            }
            return body;
        }

    }


}
