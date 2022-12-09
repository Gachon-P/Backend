package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.WeatherDto;
import kr.ac.gachon.pproject.entity.Weather;
import kr.ac.gachon.pproject.service.UserService;
import kr.ac.gachon.pproject.service.WeatherService;
import kr.ac.gachon.pproject.temp.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final UserService userService;
    @PostMapping("/createWeather")
    public ResponseEntity<Object> createWeather(@RequestBody WeatherDto weatherDto) {
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
    public ResponseEntity<Object> getCoordinate(@RequestBody String appId) {
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

    @GetMapping("/getWeather")
    public ResponseEntity<Object> getWeather(@RequestParam(value = "appId", required = false) String appId, @RequestParam(value = "macId", required = false) String macId) {
        HashMap map = new HashMap<>();

        if (appId == null && macId == null) {
            map.put("status", 490);
            map.put("errorMessage", "there is no value. you should input value appId or macId");
            return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
        } else if (appId != null && macId != null) {
            map.put("status", 490);
            map.put("errorMessage", "there is double value. you should input only one value: appId or macId");
            return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
        }

        String convertedAppId = "";
        if (appId == null && macId != null) {
            convertedAppId = userService.macIdToAppId(macId);
        } else if (appId != null && macId == null) {
            convertedAppId = appId;
        }

        if (convertedAppId == "") {
            map.put("status", 490);
            map.put("errorMessage", "app id is empty string.");
        }

        Weather weather = weatherService.getCoordinate(convertedAppId);
        double lat;
        double lon;
        String city = "송파구";
        if (weather == null) {
            lat = 37.511336;
            lon = 127.086262;
            map.put("isEmpty", true);
        } else {
            lat = weather.getLatitude();
            lon = weather.getLongitude();
            map.put("isEmpty", false);
            city = weather.getCity();
        }

        String url = "https://api.openweathermap.org/data/3.0/onecall"
                + String.format("?lat=%f&lon=%f", lat, lon)
                + "&lang=kr&exclude=alerts"
                + String.format("&appid=%s", Constant.OPEN_WEATHER_MAP_KEY)
                + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class, httpEntity);


        // res.getBody() => in JS, JSON.parse(res.getBody()) 형태로 json 형식 변환 가능
        map.put("weatherInfo", res.getBody());
        map.put("city", city);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
