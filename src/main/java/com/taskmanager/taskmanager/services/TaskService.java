package com.taskmanager.taskmanager.services;

import com.taskmanager.taskmanager.application.dto.TaskDTO;
import com.taskmanager.taskmanager.application.exceptions.ResourceNotFoundException;
import com.taskmanager.taskmanager.application.exceptions.UnauthorizedException;
import com.taskmanager.taskmanager.domain.models.Task;
import com.taskmanager.taskmanager.domain.models.User;
import com.taskmanager.taskmanager.infrastructure.repositories.TaskRepository;
import com.taskmanager.taskmanager.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(TaskDTO taskDTO, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Task task = new Task();

        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setPriority(taskDTO.priority());
        task.setStatus(taskDTO.status());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUser(user);

        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(Long userId){
        return taskRepository.findUserById(userId);
    }

    public Task updateTask(Long taskId, TaskDTO taskDTO, Long userId){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        if(!task.getUser().getId().equals(userId)){
            throw new UnauthorizedException("Usuário não tem permissão para alterar esta tarefa");
        }

        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setPriority(taskDTO.priority());
        task.setStatus(taskDTO.status());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
        if(!task.getUser().getId().equals(userId)){
            throw new UnauthorizedException("Usuário não tem permissão para deletar esta tarefa");
        }

        taskRepository.delete(task);
    }
}
