package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.dto.response.RentalResponse;
import com.ChaTopApiSpring2.dto.response.UserResponse;
import com.ChaTopApiSpring2.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controller handling user-related operations.
 */
@RestController
@Slf4j
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves the details of a user based on the provided user ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing the user details.
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Get user with id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
    })
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        Map<String, Object> response = userService.getUserById(id);

        UserResponse userResponse = new UserResponse();
        userResponse.setId((String) response.get("id"));
        userResponse.setName((String) response.get("name"));
        userResponse.setEmail((String) response.get("email"));
        userResponse.setCreated_at((LocalDateTime) response.get("created_at"));
        userResponse.setUpdated_at((LocalDateTime) response.get("updated_at"));

        return ResponseEntity.ok(userResponse);
    }
}
