package com.example.to_do_application.repositories;

import com.example.to_do_application.entities.Todo;
import com.example.to_do_application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByOwner(User owner);
}
