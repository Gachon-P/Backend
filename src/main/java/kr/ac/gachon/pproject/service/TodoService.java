package kr.ac.gachon.pproject.service;

import kr.ac.gachon.pproject.dto.TodoDto;
import kr.ac.gachon.pproject.entity.Todo;
import kr.ac.gachon.pproject.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    public final TodoRepository todoRepository;

    public Todo saveToDo(TodoDto todoDto) {
        Todo loadedToDo = todoRepository.findByAppId(todoDto.getAppId());
        if(loadedToDo == null) {
            Todo newTodo = this.createToDo(todoDto);
            Todo savedToDo = todoRepository.save(newTodo);
            return savedToDo;
        } else {
            loadedToDo.setTodos(todoDto.getToDos());
            Todo savedToDo = todoRepository.save(loadedToDo);
            return savedToDo;
        }
    }

    public Todo createToDo(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setAppId(todoDto.getAppId());
        todo.setTodos(todoDto.getToDos());
        return todo;
    }

    public Todo getToDo(String appId) {
        return todoRepository.findByAppId(appId);
    }
}
