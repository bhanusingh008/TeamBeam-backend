package com.kanban.kanban.service;

import com.kanban.kanban.entity.Query;
import com.kanban.kanban.repository.QueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class QueryService {
    @Autowired
    QueryRepo queryRepo;

    @Autowired
    JavaMailSender mailSender;

    public void post_query(Query query){
        try {
            Query saved = queryRepo.save(query);

            String email = query.getEmail();
            String msg = query.getMsg();

            sendMail("bhanusingh0605@gmail.com", email+" has this to say: "+msg);

        }catch (Exception e){
           throw new RuntimeException("Something Went Wrong");
        }
    }

    public void sendMail(String toEmail, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bookingvaccine3@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Message For You");
        message.setText(text);
        mailSender.send(message);
    }
}
