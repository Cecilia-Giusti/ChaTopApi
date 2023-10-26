package com.ChaTopApiSpring2.dto.request;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) for user registration.
 */
@Data
public class RegisterRequest {

    /**
     * The name of the user.
     * This field is mandatory.
     */
    @NotNull(message = "Name cannot be empty")
    private String name;

    /**
     * The email of the user.
     * This field is mandatory and must be a valid email format.
     */
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Enter a valid email")
    private String email;

    /**
     * The password for the user.
     * This field is mandatory and must meet specific criteria:
     * at least 8 characters in length, contain at least 1 uppercase letter,
     * 1 digit, and 1 special character.
     */
    @NotNull(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\d])(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must be at least 8 characters long, contain at least 1 uppercase letter, 1 digit and 1 special character.")
    private String password;
}
