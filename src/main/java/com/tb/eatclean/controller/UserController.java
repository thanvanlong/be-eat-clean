package com.tb.eatclean.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.user.Role;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.UserRepo;
import com.tb.eatclean.service.mail.EmailSender;
import com.tb.eatclean.service.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailSender mailService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${client.url.active}")
    private String activeUrl;

    @Value("${client.url.active.redirect}")
    private String redirectUrl;



//    @PostConstruct
    public void init() throws Exception {
        User user = new User();
        user.setPassword("123123");
        user.setEmail("longthan@gmail.com");

        userService.save(user);

//        Thread.sleep(1000);

        User user1 = new User();
        user.setPassword("123123");
        user.setEmail("longthan1@gmail.com");

        userService.save(user1);

//        Thread.sleep(1000);

        User user2 = new User();
        user.setPassword("123123");
        user.setEmail("longthan2@gmail.com");

        userService.save(user2);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<User>> register(@Valid @RequestBody User payload) {
        try {
            Collection<Role> roles = new ArrayList<>();
            roles.add(Role.ROLE_ADMIN);
            payload.setRoles(roles);
            userService.save(payload);
            String uuidToken = UUID.randomUUID().toString().replace("-", "");
            System.out.println(uuidToken);
            redisTemplate.opsForValue().set(uuidToken, payload.getEmail());
            redisTemplate.expireAt(uuidToken, Instant.now().plusSeconds(300));
            mailService.send(payload.getEmail(), activeUrl + uuidToken, "active");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(null, "400", e.getMessage(), false));
        }

        return ResponseEntity.ok(new ResponseDTO<>(null, "200", "Success", true));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/active")
    public void activeUser(@RequestParam("token") String token, HttpServletResponse response) {
        if (token != null && redisTemplate.opsForValue().get(token) != null) {
            String email = (String) redisTemplate.opsForValue().get(token);
            User user = userService.findByEmail(email);
            try {
                if (user != null) {
                    user.setIsActive(true);
                    userService.update(user);
                    response.sendRedirect(redirectUrl + "/success");
                } else {
                    response.sendRedirect(redirectUrl + "/fail");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //TODO khong can access token

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public void get() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO principal is email of user
        System.out.println(principal);
    }

    @GetMapping("/s")
    public void getX() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO principal is email of user
        System.out.println(principal);
    }



}
