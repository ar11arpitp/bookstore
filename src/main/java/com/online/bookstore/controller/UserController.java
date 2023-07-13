
package com.online.bookstore.controller;

import com.online.bookstore.dto.request.UserRequest;
import com.online.bookstore.dto.response.SuccessResponse;
import com.online.bookstore.dto.response.UserResponse;
import com.online.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user operations.
 */
@Validated
@RestController
@Tag(name = "Auth User-Admin Controller" ,description = "Controller for handling user operations.")
@RequestMapping("v1/authorize")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Register a new Admin User.
     *
     * @param userInfo The user information for registration.
     * @return ResponseEntity containing a success response with the registered admin user.
     */
    @PostMapping("/registerAdmin")
    @Operation(summary = "Register new Admin User")
    public ResponseEntity<SuccessResponse<UserResponse>> addAdminUser(@Valid @RequestBody UserRequest userInfo) {
        UserResponse adminUser = userService.addAdminUser(userInfo);

        SuccessResponse<UserResponse> response = SuccessResponse.<UserResponse>builder()
                .data(adminUser)
                .message("AdminUser added successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Register a new User.
     *
     * @param userInfo The user information for registration.
     * @return ResponseEntity containing a success response with the registered user.
     */
    @PostMapping("/registerUser")
    @Operation(summary = "Register new User")
    public ResponseEntity<SuccessResponse<UserResponse>> addUser(@Valid @RequestBody UserRequest userInfo) {
        UserResponse user = userService.addUser(userInfo);

        SuccessResponse<UserResponse> response = SuccessResponse.<UserResponse>builder()
                .data(user)
                .message("User added successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Delete a User.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity containing a success response with the deletion message.
     */
    @DeleteMapping("/removeUserById")
    @Operation(summary = "Delete a User", security = {@SecurityRequirement(name = "basicAuth")})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse<String>> deleteUser(
            @Parameter(name = "userId", in = ParameterIn.QUERY, required = true) @RequestParam Integer userId) {
        String deletionMessage = userService.deleteUser(userId);

        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .message(deletionMessage)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Get all users.
     *
     * @return ResponseEntity containing a success response with the list of all users.
     */
    @GetMapping("/getAllUsers")
    @Operation(summary = "Get all users", security = {@SecurityRequirement(name = "basicAuth")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> allUsers = userService.getAllUsers();

        SuccessResponse<List<UserResponse>> response = SuccessResponse.<List<UserResponse>>builder()
                .data(allUsers)
                .build();

        return ResponseEntity.ok(response);
    }
}
