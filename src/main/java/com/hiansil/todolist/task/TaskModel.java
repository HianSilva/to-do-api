package com.hiansil.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID ownerId;

    @Column(length = 50, nullable = false)
    private String title;
    private String description;
    private boolean completed = false;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    @CreationTimestamp
    private LocalDate createdAt;

}
