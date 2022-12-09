package kr.ac.gachon.pproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusDto {
    private String appId;
    private String lineNumber;
    private String stationName;
    private String stationId;
    private String routeId;
    private String staOrder;
}
