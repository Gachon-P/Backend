package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.BusDto;
import kr.ac.gachon.pproject.entity.Bus;
import kr.ac.gachon.pproject.service.BusService;
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

    @GetMapping("/busList")
    public ResponseEntity<Object> busList(@RequestParam(value = "lineNumber") String lineNumber) {
        String apiURL = "http://apis.data.go.kr/6410000/busrouteservice/getBusRouteList"
                + "?serviceKey=" + Constant.DATA_API_KEY_ENCODING
                + "&keyword=" + lineNumber;

        String xml = busService.getApiXml(apiURL);
        try {
            List result = busService.xmlToJson(xml, "busRouteList");
            System.out.println("result: " + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println("result: " + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/busArrival")
    public ResponseEntity<Object> busArrival(@RequestParam(value = "stationId") String stationId, @RequestParam(value = "routeId") String routeId, @RequestParam(value = "staOrder") String staOrder) {
        String apiURL = "http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalItem"
                + "?serviceKey=" + Constant.DATA_API_KEY_ENCODING
                + "&stationId=" + stationId
                + "&routeId=" + routeId
                + "&staOrder=" + staOrder;

        String xml = busService.getApiXml(apiURL);
        try {
            List result = busService.xmlToJson(xml, "busArrivalItem");
            System.out.println("result: " + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
