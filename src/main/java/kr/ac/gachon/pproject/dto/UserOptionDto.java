package kr.ac.gachon.pproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserOptionDto {
    private String appId;
    private String sensorStart;
    private String sensorEnd;
    private String todoKeyword;
    private String weatherKeyword;
    private String busKeyword;
    private String newsKeyword;
}
