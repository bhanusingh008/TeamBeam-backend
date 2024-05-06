package com.kanban.kanban.controller;

import com.kanban.kanban.DTOs.request.LoginReq;
import com.kanban.kanban.DTOs.request.RegisterDTO;
import com.kanban.kanban.DTOs.response.LoginRes;
import com.kanban.kanban.auth.JwtUtil;
import com.kanban.kanban.entity.UserEntity;
import com.kanban.kanban.repository.UserRepository;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO){

        if(userRepository.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        System.out.println(registerDTO.toString());

        UserEntity entity = new UserEntity();
        entity.setFirstName(registerDTO.getFirstName());
        entity.setLastName(registerDTO.getLastName());
        entity.setUsername(registerDTO.getUsername());
        entity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(entity);

        return new ResponseEntity<>("User registered!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginReq req){
        try{

            Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(req.getEmail(),
                      req.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);


//            Optional<UserEntity> option = userRepository.findByEmail(req.getEmail());
//
//            if(option.isEmpty()){
//                throw  new BadCredentialsException("User not registered");
//            }
//
//            if(!option.get().getPassword().equals(req.getPassword())){
//                throw  new BadCredentialsException("Wrong Password");
//            }

//            if(!authentication.isAuthenticated()){
//                throw new BadCredentialsException("Wrong Credentials");
//            }

            UserEntity user = UserEntity.builder()
                    .username(req.getEmail())
                    .password(req.getPassword()).
                    build();
            String accessToken = jwtUtil.generateToken(user);
            LoginRes response = new LoginRes(user.getUsername(), accessToken);

//            System.out.println(accessToken);

            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
