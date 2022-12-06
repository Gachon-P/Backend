package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.WeatherDto;
import kr.ac.gachon.pproject.entity.Weather;
import kr.ac.gachon.pproject.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    @PostMapping("/createWeather")
    public ResponseEntity<Object> createWeather(@RequestBody WeatherDto weatherDto) {
        System.out.println(weatherDto);
        HashMap map = new HashMap<>();

        Weather weather = this.weatherService.createWeather(weatherDto);
        if (weather == null) {
            map.put("status", 490);
        } else {
            map.put("status", 200);
        }
        map.put("weather", weather);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/get-coordinate")
    public ResponseEntity<Object> getWeather(String appId) {
        Weather weather = this.weatherService.getCoordinate(appId);
        HashMap map = new HashMap<>();
        if (weather == null) {
            map.put("status", 490);
            map.put("errorMessage", "there is no data. please check app id or create user info");
        } else {
            map.put("status", 200);
            map.put("latitude", weather.getLatitude());
            map.put("longitude", weather.getLongitude());
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
