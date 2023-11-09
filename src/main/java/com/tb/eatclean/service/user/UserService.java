package com.tb.eatclean.service.user;

import com.tb.eatclean.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService extends UserDetailsService {
    void save(User user) throws Exception;
    void update(User user);
    void delete(User user);

    Map<String, Object> search(int page, int limit, String search);
    void find(String id);
    User findByEmail(String email);
}
