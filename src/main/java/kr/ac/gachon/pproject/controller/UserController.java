package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.UserDto;
import kr.ac.gachon.pproject.entity.User;
import kr.ac.gachon.pproject.service.UserService;
import kr.ac.gachon.pproject.temp.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        HashMap map = new HashMap<>();
        User user = userService.testUser();

        if (user == null) {
            map.put("error", "no user");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("appId", user.getAppId());
        map.put("macId", user.getMacId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        if (userDto.getMacId() == null) {
            userDto.setMacId(Constant.FRIDAY_MAC);
        }
        User user = userService.createUser(userDto);
        User saveUser = userService.connectFriday(user);
        HashMap map = new HashMap<>();

        if (saveUser == null) {
            map.put("status", 490);
        } else {
            map.put("status", 200);
            map.put("user", saveUser);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
