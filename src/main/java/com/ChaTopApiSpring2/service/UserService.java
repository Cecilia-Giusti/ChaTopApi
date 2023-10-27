package com.ChaTopApiSpring2.service;

import com.ChaTopApiSpring2.dto.request.LoginRequest;
import com.ChaTopApiSpring2.dto.request.RegisterRequest;
import com.ChaTopApiSpring2.exceptions.AccountException;
import com.ChaTopApiSpring2.exceptions.LoginException;
import com.ChaTopApiSpring2.exceptions.RegisterException;
import com.ChaTopApiSpring2.model.UserInfoModel;
import com.ChaTopApiSpring2.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for handling user-related operations.
 * This service offers methods for user authentication, registration,
 * and information retrieval using the associated repositories
 * and utility services.
 */
@Service
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.ChaTopApiSpring2.service.JwtService jwtService;

    /**
     * Retrieves user information using the email from the currently authenticated token.
     *
     * @return An optional containing the user information if found, empty otherwise.
     */
    public Optional<UserInfoModel> getUserByEmailFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String email = (String) authentication.getPrincipal();
            return userInfoRepository.findByEmail(email);
        }
        return Optional.empty();
    }

    /**
     * Registers a new user with the given details.
     *
     * @param registerRequest The user's registration details.
     * @return A JWT token generated for the registered user.
     * @throws RegisterException if registration fails, typically due to existing email.
     */
    public String registerUser(RegisterRequest registerRequest) {
        Optional<UserInfoModel> existingUser = userInfoRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new RegisterException("Bad name, email or password");
        }
        UserInfoModel newUser = new UserInfoModel();
        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());

        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


        userInfoRepository.save(newUser);

        return jwtService.generateToken(newUser.getEmail());
    }

    /**
     * Authenticates a user based on the provided login details.
     *
     * @param loginRequest The user's login details.
     * @return A JWT token for the authenticated user.
     * @throws LoginException if authentication fails due to incorrect credentials.
     */
    public String loginUser(LoginRequest loginRequest) {

        Optional<UserInfoModel> userOptional = userInfoRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {

            UserInfoModel user = userOptional.get();


            if  (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

                return jwtService.generateToken(user.getEmail());
            } else {
                throw new LoginException(" Bad token");
            }
        } else {
            throw new LoginException(" Bad token");
        }
    }

    /**
     * Retrieves the information of the user based on the authentication token.
     *
     * @return The UserInfoModel object of the authenticated user.
     * @throws AccountException if the user associated with the token is not found.
     */
    public UserInfoModel getUserInfo() {
        Optional<UserInfoModel> userOptional = getUserByEmailFromToken();

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new AccountException("bad token");
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The UserInfoModel object for the specified user ID.
     * @throws AccountException if a user with the given ID is not found.
     */
    public UserInfoModel getUserById(int id) {
        Optional<UserInfoModel> userId = userInfoRepository.findById(id);

        return userId.orElseThrow(() -> new AccountException("User not found"));
    }

}
