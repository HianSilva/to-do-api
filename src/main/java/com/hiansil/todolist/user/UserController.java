package com.hiansil.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserModel user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            System.out.println("Username already exists: " + user.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }

        var createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
