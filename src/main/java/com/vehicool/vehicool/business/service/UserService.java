package com.vehicool.vehicool.business.service;

import com.vehicool.vehicool.persistence.entity.User;
import com.vehicool.vehicool.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElse(null);
    }

}
