package com.online.bookstore.dto.request;

import com.online.bookstore.common.constants.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for User API")
public class UserRequest {

    @NotNull(message = "firstName " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "firstName " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "First name", example = "Arpit")
    private String firstName;

    @NotNull(message = "lastName " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "lastName " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "Last name", example = "Patidar")
    private String lastName;

    @NotNull(message = "Email Address " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "Email Address " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "Email address", example = "arpitp@xyz.com")
    private String email;

    @Size(min = 4, max = 16)
    @NotNull(message = "username " + AppConstants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "username " + AppConstants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "Username should be atleast between 4 to 16 character", example = "arpitp")
    private String username;

    @Schema(description = "User's password", example = "Password@123", pattern = AppConstants.Regex.PASSWORD_REGEX)
    private String password;
}
