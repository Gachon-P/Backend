package kr.ac.gachon.pproject.controller;

import kr.ac.gachon.pproject.dto.TodoDto;
import kr.ac.gachon.pproject.entity.Todo;
import kr.ac.gachon.pproject.service.TodoService;
import kr.ac.gachon.pproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;
    @GetMapping("/getToDo")
    public ResponseEntity<Object> getToDo(@RequestParam(value = "appId", required = false) String appId, @RequestParam(value = "macId", required = false) String macId) {
        System.out.println("------- get to do list -------");

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

        Todo savedToDos = todoService.getToDo(convertedAppId);
        if (savedToDos == null) {
            map.put("status", 490);
            map.put("errorMessage", "there is no todo info. please add bus info");

            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        map.put("todoInfo", savedToDos);
        map.put("status", 200);
        System.out.println(map);
        System.out.println("------------------------------");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/updateToDo")
    public ResponseEntity<Object> updateToDo(@RequestBody TodoDto todoDto) {
        System.out.println("------- update todo list -------");

        HashMap map = new HashMap<>();
        Todo savedTodo =  todoService.saveToDo(todoDto);
        map.put("savedTodos", savedTodo);
        map.put("status", 200);
        System.out.println(map);
        System.out.println("--------------------------------");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

//    @PostMapping("/fridayToDo")
//    public ResponseEntity<Object> fridayToDo(@RequestBody TodoDto todoDto) {
//
//    }
}
