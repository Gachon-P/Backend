package kr.ac.gachon.pproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherDto {
    private String appId;
    private double latitude;
    private double longitude;
}
