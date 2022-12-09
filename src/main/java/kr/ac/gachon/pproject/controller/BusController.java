package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.BusDto;
import kr.ac.gachon.pproject.entity.Bus;
import kr.ac.gachon.pproject.service.BusService;
import kr.ac.gachon.pproject.service.UserService;
import kr.ac.gachon.pproject.temp.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;
    private final UserService userService;
    @PostMapping("/updateBus")
    public ResponseEntity<Object> updateBus(@RequestBody BusDto busDto) {
        HashMap map = new HashMap<>();
        Bus bus = busService.createBus(busDto);
        if (bus == null) {
            map.put("status", 490);
            map.put("errorMessage", "there is no user app id");
        } else {
            map.put("status", 200);
            map.put("bus", bus);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/getBus")
    public ResponseEntity<Object> getBus(@RequestParam(value = "appId") String appId) {
        HashMap map = new HashMap<>();
        Bus bus = busService.getBusInfo(appId);

        if (bus == null) {
            map.put("status", 490);
            map.put("errorMessage", "there is no bus info.");
        } else {
            map.put("status", 200);
            map.put("stationName", bus.getStationName());
            map.put("lineNumber", bus.getLineNumber());
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/busList")
    public ResponseEntity<Object> busList(@RequestParam(value = "lineNumber") String lineNumber) {
        String apiURL = "http://apis.data.go.kr/6410000/busrouteservice/getBusRouteList"
                + "?serviceKey=" + Constant.DATA_API_KEY_ENCODING
                + "&keyword=" + lineNumber;

        String xml = busService.getApiXml(apiURL);
        try {
            List result = busService.xmlToJson(xml, "busRouteList");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/busStation")
    public ResponseEntity<Object> busStation(@RequestParam(value = "routeId") String routeId) {
        String apiURL = "http://apis.data.go.kr/6410000/busrouteservice/getBusRouteStationList"
                + "?serviceKey=" + Constant.DATA_API_KEY_ENCODING
                + "&routeId=" + routeId;

        String xml = busService.getApiXml(apiURL);
        try {
            List result = busService.xmlToJson(xml, "busRouteStationList");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/busArrival")
    public ResponseEntity<Object> busArrival(@RequestParam(value = "appId", required = false) String appId, @RequestParam(value = "macId", required = false) String macId) {
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
            return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
        }

        Bus bus = busService.getBusInfo(convertedAppId);
        if (bus == null) {
            map.put("status", 490);
            map.put("errorMessage", "there is no bus info. please add bus info");

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        String apiURL = "http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalItem"
                + "?serviceKey=" + Constant.DATA_API_KEY_ENCODING
                + "&stationId=" + bus.getStationId()
                + "&routeId=" + bus.getRouteId()
                + "&staOrder=" + bus.getStaOrder();

        String xml = busService.getApiXml(apiURL);

        try {
            List result = busService.xmlToJson(xml, "busArrivalItem");
            map.put("arrivalInfo", result);
            map.put("stationName", bus.getStationName());
            map.put("lineNumber", bus.getLineNumber());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
