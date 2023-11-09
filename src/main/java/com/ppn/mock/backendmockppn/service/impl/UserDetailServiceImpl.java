package com.ppn.mock.backendmockppn.service.impl;

import com.ppn.mock.backendmockppn.entities.CustomUserDetails;
import com.ppn.mock.backendmockppn.entities.User;
import com.ppn.mock.backendmockppn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetails(user);
    }
}
