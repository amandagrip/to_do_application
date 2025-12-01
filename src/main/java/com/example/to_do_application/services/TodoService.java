package com.example.to_do_application.services;

import com.example.to_do_application.entities.Todo;
import com.example.to_do_application.entities.User;
import com.example.to_do_application.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodosForUser(User user) {
        return todoRepository.findAllByOwner(user);
    }

    public Todo createTodo(Todo todo, User owner) {
        todo.setOwner(owner); // sätt ägare
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo, User currentUser) {
        boolean isOwner = todo.getOwner().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You cannot update this todo");
        }

        return todoRepository.save(todo);
    }

    public void deleteTodo(Todo todo, User currentUser) {
        boolean isOwner = todo.getOwner().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You cannot delete this todo");
        }

        todoRepository.delete(todo);
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }
}

