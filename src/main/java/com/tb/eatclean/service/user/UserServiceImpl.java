package com.tb.eatclean.service.user;

import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepo.findUserByEmail(username).isPresent()) {
            return userRepo.findUserByEmail(username).get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
