package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.UserDto;
import kr.ac.gachon.pproject.entity.User;
import kr.ac.gachon.pproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<Object> test(@RequestBody UserDto userDto) {
        HashMap map = new HashMap<>();

        map.put("test key", "test value");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/default")
    public ResponseEntity<Object> test() {
        HashMap map = new HashMap<>();
        map.put("json key", "json value");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        System.out.println(userDto);
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
