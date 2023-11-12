package com.tb.eatclean.service.user;

import com.tb.eatclean.config.PassEncoder;
import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.promotion.Promotion;
import com.tb.eatclean.entity.user.Role;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.UserRepo;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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
//            throw new Exception("Account has already exist");
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
                            it.mapping(user);
                            it.setPassword(passwordEncoder.encode(user.getPassword()));
                            userRepo.save(it);
                        },
                        () -> {
                            throw new RuntimeException("User not found");
                        }
                );
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public Map<String, Object> search(int page, int limit, String search) {
        Pageable pagingSort = PageRequest.of(page, limit);
        Page<User> userPage = userRepo.findAll(pagingSort);

        Metadata metadata = new Metadata();
        metadata.setPageNumber(userPage.getNumber());
        metadata.setPageSize(userPage.getSize());
        metadata.setTotalPages(userPage.getTotalPages());
        metadata.setTotalItems(userPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("results", userPage.getContent().stream().filter(it -> !it.getRoles().contains(Role.ROLE_ADMIN)));
        response.put("metadata", metadata);

        return response;
    }

    @Override
    public void find(String id) {

    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepo.findUserByEmail(email);
        return userOptional.isPresent() ? userOptional.get() : null;
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
