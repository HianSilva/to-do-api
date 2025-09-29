package com.hiansil.todolist.task;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity createTask(@RequestBody TaskModel task, HttpServletRequest request) {
        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())){
            return ResponseEntity.badRequest().body("Start and End date must be in bigger than current date");
        }

        if (task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.badRequest().body("End date must be in bigger than Start date");
        }

        var userId = (UUID) request.getAttribute("userId");
        task.setOwnerId(userId);
        taskRepository.save(task);
        return ResponseEntity.status(201).body(task);
    }

    @GetMapping
    public ResponseEntity getTasks(HttpServletRequest request) {
        var userId = (UUID) request.getAttribute("userId");
        var tasks = taskRepository.findByOwnerId(userId);
        return ResponseEntity.ok(tasks);
    }

}
