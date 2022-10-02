package com.nrgroup.bucket.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nrgroup.bucket.entity.User;
import com.nrgroup.bucket.entity.enumeration.Role;
import com.nrgroup.bucket.user.model.request.RegisterRequest;
import com.nrgroup.bucket.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private AuthenticationProvider authenticationProvider;

    public User findUser(String username) {
        // Authentication authentication = authenticationProvider
        // .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // return authentication;
        return new User("11234567", "navin", "$2a$10$lTOqdpbfVzu6IfNOZoXG8.xE6qDkwM6jNuw8naeygXSP61M6sPwQO", Role.USER);
    }

    public String registerUser(RegisterRequest registerRequest) {
        log.info("Registering user - {}", registerRequest.getEmail());
        try {
            User user = new User();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setPassword(encoder.encode(registerRequest.getPassword()));
            user.setEmail(registerRequest.getEmail());
            user.setGender(registerRequest.getGender());
            user.setRole(Role.USER);
            String userNo = userRepository.createUser(user);
            if (userNo == null) {
                log.info("User registered successfully - {}", registerRequest.getEmail());
                return userNo;
            }
        } catch (Exception e) {
            log.error("Exception Registering User - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(String email, String password) {
        log.info("Updating password for user - {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.get().setPassword(encoder.encode(password));
            userRepository.updateUserPassword(user.get());
            log.info("Password updated for user - {}", email);
            return true;
        } else {
            log.info("User not found - {}", email);
        }
        return false;
    }

    public Boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
