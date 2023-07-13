package com.online.bookstore.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response object for User API")
public class UserResponse extends BaseResponse<Integer> {

    @Schema(description = "First name")
    private String firstName;

    @Schema(description = "Last name")
    private String lastName;

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Email")
    private String email;
}
