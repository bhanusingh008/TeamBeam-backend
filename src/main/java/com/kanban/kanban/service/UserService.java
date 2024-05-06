package com.kanban.kanban.service;

import com.kanban.kanban.DTOs.request.LoginReq;
import com.kanban.kanban.entity.UserEntity;
import com.kanban.kanban.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity addUser(LoginReq userRequestDTO){

        // generate hashcode

        UserEntity curr = UserEntity.builder()
                .username(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword()).build();

        return userRepository.save(curr);
    }
}
