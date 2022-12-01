package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.BusDto;
import kr.ac.gachon.pproject.entity.Bus;
import kr.ac.gachon.pproject.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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
}
