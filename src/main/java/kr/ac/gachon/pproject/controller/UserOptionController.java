package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.UserOptionDto;
import kr.ac.gachon.pproject.entity.UserOption;
import kr.ac.gachon.pproject.repository.UserOptionRepository;
import kr.ac.gachon.pproject.service.UserOptionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/userOption")
@RequiredArgsConstructor
public class UserOptionController {

    private final UserOptionRepository userOptionRepository;
    private final UserOptionService userOptionService;
    @GetMapping("/getUserOption")
    public ResponseEntity<Object> getUserOption(@RequestParam(value = "appId", required = true) String appId) {
        HashMap map = new HashMap<>();
        UserOption userOption = userOptionRepository.findByAppId(appId);
        if(userOption == null) {
            map.put("status", 490);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } else {
            map.put("status", 200);
            map.put("userOption", userOption);

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping("/setUserOption")
    public ResponseEntity<Object> setUserOption(@RequestBody UserOptionDto userOptionDto) {
        HashMap map = new HashMap<>();
        map.put("status", 200);
        UserOption userOption = userOptionService.createUserOption(userOptionDto);
        map.put("userOption", userOption);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
