package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.dto.response.UserResponse;
import com.ChaTopApiSpring2.model.UserInfoModel;
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

import java.time.ZoneId;


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
            @ApiResponse(code = 401, message = "Bad token", response = String.class)
    })
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        UserInfoModel user = userService.getUserById(id);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());

        if (user.getCreatedAt() != null) {
            userResponse.setCreated_at(user.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }

        if (user.getUpdatedAt() != null) {
            userResponse.setUpdated_at(user.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        } else {
            userResponse.setUpdated_at(null);
        }

        return ResponseEntity.ok(userResponse);
    }
}
