
package com.campuscareer.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.campuscareer.backend.entity.User;
import com.campuscareer.backend.repository.UserRepository;

@RestController
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://campus-career-frontend.onrender.com"
})
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/register")
    public User register(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/api/login")
    public User login(@RequestBody User user) {
        return userRepository.findByEmail(user.getEmail())
                .filter(existingUser ->
                        existingUser.getPassword().equals(user.getPassword()))
                .orElse(null);
    }
}
