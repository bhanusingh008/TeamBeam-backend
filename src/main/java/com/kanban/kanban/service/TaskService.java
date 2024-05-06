package com.kanban.kanban.service;

import com.kanban.kanban.entity.Task;
import com.kanban.kanban.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepo;

    public List<Task> getAllTodo() {
        List<Task>  list = taskRepo.findAll();

        if(list.size()==0){
            return null;
        }

        List<Task> toDo = new ArrayList<>();

        for(Task task : list){
            if(task.getState().equals("To-Do")){
                toDo.add(task);
            }
        }

        return toDo;
    }

    public List<Task> getAllDoing() {
        List<Task>  list = taskRepo.findAll();

        if(list.size()==0){
            return null;
        }

        List<Task> Doing = new ArrayList<>();

        for(Task task : list){
            if(task.getState().equals("Doing")){
                Doing.add(task);
            }
        }

        return Doing;
    }

    public List<Task> getAllDone() {
        List<Task>  list = taskRepo.findAll();

        if(list.size()==0){
            return null;
        }

        List<Task> Done = new ArrayList<>();

        for(Task task : list){
            if(task.getState().equals("Done")){
                Done.add(task);
            }
        }
        return Done;
    }

    public void addTask(Task task) {
        if(task.getState()!=null){
            taskRepo.save(task);
        }else{
            throw new RuntimeException("State not defined");
        }
    }

    public void deleteById(int id) {
        taskRepo.deleteById(id);
    }
}
