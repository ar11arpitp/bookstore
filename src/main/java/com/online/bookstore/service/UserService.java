
package com.online.bookstore.service;

import com.online.bookstore.dto.request.UserRequest;
import com.online.bookstore.dto.response.UserResponse;
import com.online.bookstore.entity.User;

import java.util.List;

public interface UserService {
    UserResponse addAdminUser(UserRequest userRequest);

    UserResponse addUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUser(int id);

    String deleteUser(Integer userId);

    User retrieveCurrentLoginUser();
}
