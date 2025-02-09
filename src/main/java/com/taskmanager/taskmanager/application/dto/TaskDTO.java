package com.taskmanager.taskmanager.application.dto;

import com.taskmanager.taskmanager.domain.enums.TaskPriority;
import com.taskmanager.taskmanager.domain.enums.TaskStatus;

public record TaskDTO (
        String title,
        String description,
        TaskPriority priority,
        TaskStatus status
){}
