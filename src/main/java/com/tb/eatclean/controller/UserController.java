package com.tb.eatclean.controller;


import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.service.mail.EmailSender;
import com.tb.eatclean.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailSender mailService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<User>> register(@Valid @RequestBody User payload) {
        try {
            payload.setPassword("123123");
            userService.save(payload);
//            mailService.send(payload.getEmail(), payload.getEmail(), "pass");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "sdfghjkl;");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(null, "400", e.getMessage(), false));
        }

        return ResponseEntity.ok(new ResponseDTO<>(null, "200", "Success", true));
    }


    //TODO khong can access token
    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public void get() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO principal is email of user
        System.out.println(principal);
    }

    @PreAuthorize("hasAuthority('ROLE_VIEWER')")
    @GetMapping("/s")
    public void getX() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO principal is email of user
        System.out.println(principal);
    }



}
