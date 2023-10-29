package com.tb.eatclean.service.user;

import com.tb.eatclean.config.PassEncoder;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.UserRepo;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional

    public void save(User user) throws Exception{
        if (userRepo.findUserByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Account has already exist");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        }
    }

    @Override
    public void update(User user) {
        userRepo.findUserByEmail(user.getEmail())
                .ifPresentOrElse(
                        (it) -> {
                            user.setPassword(passwordEncoder.encode(user.getPassword()));
                            it.mapping(user);
                            userRepo.save(it);
                        },
                        () -> {
                            throw new RuntimeException("User not found");
                        }
                );
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void find(String id) {

    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findUserByEmail(email).get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        if (userRepo.findUserByEmail(email).isPresent()) {
            User user = userRepo.findUserByEmail(email).get();
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
