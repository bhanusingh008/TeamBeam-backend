package com.kanban.kanban.controller;

import com.kanban.kanban.entity.Task;
import com.kanban.kanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TaskController {
    @Autowired
    TaskService service;

    @GetMapping("/todo")
    public List<Task> getAllTodo(){
        return service.getAllTodo();
    }

    @PostMapping("/task")
    public void addTask(@RequestBody Task task){
        service.addTask(task);
    }

    @GetMapping("/doing")
    public List<Task> getAllDoing(){
        return service.getAllDoing();
    }

    @GetMapping("/done")
    public List<Task> getAllDone(){
        return service.getAllDone();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") int id){
        service.deleteById(id);
    }
}
