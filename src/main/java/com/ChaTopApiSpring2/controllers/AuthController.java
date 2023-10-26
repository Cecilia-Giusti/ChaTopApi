package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.dto.request.RegisterRequest;
import com.ChaTopApiSpring2.dto.response.TokenResponse;
import com.ChaTopApiSpring2.dto.response.UserResponse;
import com.ChaTopApiSpring2.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Controller handling authentication operations such as user registration and login.
 */

@RestController
@Slf4j
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user and returns an authentication token.
     *
     * @param registerRequest The details of the user to register.
     * @return A ResponseEntity containing the authentication token for the newly registered user.
     */
    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TokenResponse.class),
    })
    public ResponseEntity<TokenResponse> createUser( @RequestBody @Valid RegisterRequest registerRequest) {
        String token = userService.registerUser(registerRequest);
        TokenResponse tokenResponse = new TokenResponse(token);
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * Authenticates a user and returns an authentication token.
     *
     * @param loginDto The details of the user for logging in.
     * @return A ResponseEntity containing the authentication token for the logged-in user.
     */
    @PostMapping("/login")
    @ApiOperation(value = "Log in the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TokenResponse.class),
    })
    public ResponseEntity<TokenResponse> loginUser(@RequestBody RegisterRequest loginDto) {
        String token = userService.loginUser(loginDto);
        TokenResponse tokenResponse = new TokenResponse(token);
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * Retrieves the information of the authenticated user.
     *
     * @param request The incoming HTTP request.
     * @return A map containing the user's information.
     */
    @GetMapping("/me")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
    })
    public ResponseEntity<UserResponse> getUserInfo(HttpServletRequest request) {
        Map<String, Object> response = userService.getUserInfo();
        UserResponse userResponse = new UserResponse(response);
        return ResponseEntity.ok(userResponse);
    }
}

