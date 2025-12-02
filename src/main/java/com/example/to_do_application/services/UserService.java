package com.example.to_do_application.services;

import com.example.to_do_application.controllers.RegisterRequest;
import com.example.to_do_application.entities.Role;
import com.example.to_do_application.entities.User;
import com.example.to_do_application.repositories.RoleRepository;
import com.example.to_do_application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String username, String password) {
        if (userRepository.existsByUserName(username)) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUserName(username);
        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        return userRepository.save(user);

    }

    public Optional<User> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public void deleteUser(User targetUser, User currentUser) {
        boolean currentUserIsAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!currentUserIsAdmin) {
            throw new RuntimeException("Only admins can delete users");
        }

        userRepository.delete(targetUser);
    }

    public Set<User> getAllUsers(User currentUser) {
        boolean currentUserIsAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!currentUserIsAdmin) {
            throw new RuntimeException("Only admins can view all users");
        }

        return new HashSet<>(userRepository.findAll());
    }

    public void registerUser(RegisterRequest request) {

    }
}

