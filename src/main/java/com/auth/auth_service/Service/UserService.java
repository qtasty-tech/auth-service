package com.auth.auth_service.Service;


import com.auth.auth_service.dto.UserDTO;
import com.auth.auth_service.model.User;

public interface UserService {
    User createUser(UserDTO user);

    String loginUser(UserDTO userDTO);

    User updateUser(String id, UserDTO userDTO);

    void deleteUser(String id);
}