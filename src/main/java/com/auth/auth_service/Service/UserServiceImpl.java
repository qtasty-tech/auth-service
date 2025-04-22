package com.auth.auth_service.Service;

import com.auth.auth_service.config.JwtProvider;
import com.auth.auth_service.dto.UserDTO;
import com.auth.auth_service.model.User;
import com.auth.auth_service.model.enums.UserRole;
import com.auth.auth_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            logger.error("User already exists with email: {}", userDTO.getEmail());
            throw new IllegalArgumentException("User already exists with this email!");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFname(userDTO.getFname());
        user.setLname(userDTO.getLname());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setUserType(UserRole.valueOf(userDTO.getUserType() != null ? userDTO.getUserType().toUpperCase() : "NORMAL"));

        User savedUser = userRepository.save(user);
        logger.info("User successfully created with ID: {}", savedUser.getId());

        return savedUser;
    }

    @Override
    public String loginUser(UserDTO userdto) {
        Optional<User> userOptional = userRepository.findByEmail(userdto.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userdto.getPassword(), user.getPassword())) {
                logger.info("User successfully logged in with email: {}", userdto.getEmail());
                return JwtProvider.generateToken(user);
            }
        }
        return null;
    }

    @Override
    public User updateUser(String id, UserDTO userDTO) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            logger.error("User not found with ID: {}", id);
            throw new IllegalArgumentException("User not found!");
        }

        User existingUser = existingUserOpt.get();
        existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        existingUser.setUserType(UserRole.valueOf(userDTO.getUserType() != null ? userDTO.getUserType().toUpperCase() : "NORMAL"));

        User updatedUser = userRepository.save(existingUser);
        logger.info("User successfully updated {}", updatedUser.getId());
        return updatedUser;
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found!");
        }
        userRepository.deleteById(id);
        logger.info("User with ID: {} successfully deleted", id);
    }


}