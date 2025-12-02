package com.example.to_do_application.controllers;

import com.example.to_do_application.entities.Todo;
import com.example.to_do_application.entities.User;
import com.example.to_do_application.repositories.TodoRepository;
import com.example.to_do_application.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoController(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Todo> getTodos(Authentication auth) {
        User user = userRepository.findByUserName(auth.getName()).orElseThrow();
        return todoRepository.findAllByOwner(user);
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo, Authentication auth) {
        User user = userRepository.findByUserName(auth.getName()).orElseThrow();
        todo.setOwner(user);
        return todoRepository.save(todo);
    }
}
