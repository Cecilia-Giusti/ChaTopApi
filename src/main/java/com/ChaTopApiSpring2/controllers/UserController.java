package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getUserbyId(@PathVariable int id) {
        Map<String, Object> response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }
}
