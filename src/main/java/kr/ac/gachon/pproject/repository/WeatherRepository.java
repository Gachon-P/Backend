package kr.ac.gachon.pproject.repository;

import kr.ac.gachon.pproject.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findByAppId(String appId);
}
