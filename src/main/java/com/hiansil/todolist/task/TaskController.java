package com.hiansil.todolist.task;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity createTask(@RequestBody TaskModel task, HttpServletRequest request) {
        var userId = (UUID) request.getAttribute("userId");
        task.setOwnerId(userId);
        taskRepository.save(task);
        return ResponseEntity.status(201).body(task);
    }

}
