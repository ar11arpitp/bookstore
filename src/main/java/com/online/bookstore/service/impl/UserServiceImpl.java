package com.online.bookstore.service.impl;

import com.online.bookstore.common.constants.AppConstants;
import com.online.bookstore.dto.request.UserRequest;
import com.online.bookstore.dto.response.UserResponse;
import com.online.bookstore.entity.User;
import com.online.bookstore.common.enums.RoleEnums;
import com.online.bookstore.exception.handler.CustomDataException;
import com.online.bookstore.exception.handler.NotFoundCustomException;
import com.online.bookstore.repository.UserRepository;
import com.online.bookstore.service.UserService;
import com.online.bookstore.common.enums.ErrorCodeEnums;
import com.online.bookstore.common.enums.SuccessCodeEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates an admin user with the provided details.
     *
     * @param userRequest The user details.
     * @return The created admin user response.
     */
    @Override
    public UserResponse addAdminUser(UserRequest userRequest) {
        validateRequest(userRequest);
        return modelMapper.map(this.createUser(userRequest, Collections.singletonList(RoleEnums.ROLE_ADMIN.toString())), UserResponse.class);
    }

    private void validateRequest(UserRequest userRequest) {
        List<String> errors = new ArrayList<>();
        if(Objects.nonNull(userRequest)) {
            if(Objects.nonNull(userRequest.getEmail())){
                if(!userRequest.getEmail().matches(AppConstants.Regex.EMAIL_REGEX)){
                    errors.add("Please enter correct the email");
                }
                if(!userRequest.getPassword().matches(AppConstants.Regex.PASSWORD_REGEX)){
                    errors.add("Password must contain at least one capital letter, one small letter, and one number.");
                }
            }
            if(errors.size()>0){
                throw new CustomDataException(String.join(" | ", errors) ,"ERR-11");
            }
        }
    }
    /**
     * Creates a regular user with the provided details.
     *
     * @param userRequest The user details.
     * @return The created user response.
     */
    @Override
    public UserResponse addUser(UserRequest userRequest) {
        validateRequest(userRequest);
        return modelMapper.map(this.createUser(userRequest, Collections.singletonList(RoleEnums.ROLE_USER.toString())), UserResponse.class);
    }

    /**
     * Retrieves the user with the specified ID.
     *
     * @param id The user ID.
     * @return The user response.
     * @throws NotFoundCustomException If the user is not found.
     */
    @Override
    public UserResponse getUser(int id) {
        return this.modelMapper.map(repository.findById(id).orElseThrow(() -> {
            log.info(String.format(ErrorCodeEnums.NOT_FOUND_ERR.getMessage(), id));
            return new NotFoundCustomException(ErrorCodeEnums.NOT_FOUND_ERR.getErrorCode(), String.format(ErrorCodeEnums.NOT_FOUND_ERR.getMessage(), id));
        }), UserResponse.class);
    }

    /**
     * Retrieves all users.
     *
     * @return The list of user responses.
     */
    @Override
    public List<UserResponse> getAllUsers() {
        return modelMapper.map(repository.findAll(), new TypeToken<List<UserResponse>>() {}.getType());
    }

    /**
     * Deletes the user with the specified ID.
     *
     * @param userId The user ID.
     * @return The success message upon successful deletion.
     * @throws NotFoundCustomException If the user is not found.
     */
    @Override
    public String deleteUser(Integer userId) {
        return this.repository.findById(userId).map(user -> {
            this.repository.delete(user);
            return String.format(SuccessCodeEnums.DELETED_SUCCESS.getMessage(), user.getId());
        }).orElseThrow(() -> {
            log.info(String.format(ErrorCodeEnums.NOT_FOUND_ERR.getMessage(), userId));
            return new NotFoundCustomException(ErrorCodeEnums.NOT_FOUND_ERR.getErrorCode(), String.format(ErrorCodeEnums.NOT_FOUND_ERR.getMessage(), userId));
        });
    }

    /**
     * Retrieves the current logged-in user.
     *
     * @return The current logged-in user.
     */
    @Override
    public User retrieveCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Username: {}", username);
        return this.findUserByUsername(username);
    }

    private User createUser(UserRequest userRequest, List<String> authorities) {
        userRequest.setPassword(encodePassword(userRequest.getPassword()));
        userRequest.setUsername(userRequest.getUsername().toLowerCase());
        User user = modelMapper.map(userRequest, User.class);
        user.setAuthorities(authorities);
        return repository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private User findUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
