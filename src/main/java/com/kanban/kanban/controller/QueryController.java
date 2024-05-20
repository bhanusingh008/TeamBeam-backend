package com.kanban.kanban.controller;

import com.kanban.kanban.entity.Query;
import com.kanban.kanban.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://teambeam.in", allowCredentials = "true")
public class QueryController {

    @Autowired
    QueryService queryService;

    @PostMapping("/query")
    public ResponseEntity post_query(@RequestBody Query query){

        String email = query.getEmail();
        String msg = query.getMsg();

        if(email.isEmpty() || msg.isEmpty()){
            return new ResponseEntity<>("Fill in the blanks", HttpStatus.BAD_REQUEST);
        }
        queryService.post_query(query);
        return new ResponseEntity<>("saved", HttpStatus.CREATED);
    }
}
