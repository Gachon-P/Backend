package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.dto.WeatherDto;
import kr.ac.gachon.pproject.entity.Weather;
import kr.ac.gachon.pproject.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public Weather createWeather(WeatherDto weatherDto) {
        if (weatherDto.getAppId() == "") {
            return null;
        }
        Weather validWeather = this.weatherRepository.findByAppId(weatherDto.getAppId());
        if (validWeather == null) {
            Weather weather = new Weather();
            weather.setAppId(weatherDto.getAppId());
            weather.setLatitude(weatherDto.getLatitude());
            weather.setLongitude(weatherDto.getLongitude());

            Weather saveWeather = this.weatherRepository.save(weather);
            return saveWeather;
        } else {
            validWeather.setLatitude(weatherDto.getLatitude());
            validWeather.setLongitude(weatherDto.getLongitude());

            Weather saveWeather = this.weatherRepository.save(validWeather);
            return saveWeather;
        }
    }

    public Weather getCoordinate(String appId) {
        Weather weather = this.weatherRepository.findByAppId(appId);
        return weather;
    }
}
