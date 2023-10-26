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
     * Retrieves user information using the email from the currently authenticated token.
     *
     * @return An optional containing the user information if found, empty otherwise.
     */
    public String registerUser(RegisterRequest registerRequest) {
        // Vérifiez si l'email est déjà utilisé
        Optional<UserInfoModel> existingUser = userInfoRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new RegisterException("Bad name, email or password");
        }
        // Création du nouvel utilisateur
        UserInfoModel newUser = new UserInfoModel();
        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());

        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Sauvegardez l'utilisateur dans la base de données
        userInfoRepository.save(newUser);

        // Création du token
        return jwtService.generateToken(newUser.getEmail());
    }

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequest The details required for user authentication.
     * @return A JWT token for the authenticated user.
     * @throws LoginException if authentication fails.
     */
    public String loginUser(LoginRequest loginRequest) {
        // Vérification de l'email
        Optional<UserInfoModel> userOptional = userInfoRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            // Récupération de l'utilisateur
            UserInfoModel user = userOptional.get();

            // Vérification du mot de passe crypter
            if  (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // Si le mot de passe est correct, générez un token JWT
                return jwtService.generateToken(user.getEmail());
            } else {
                throw new LoginException(" Bad token");
            }
        } else {
            throw new LoginException(" Bad token");
        }
    }

    /**
     * Retrieves the detailed information of the currently authenticated user.
     *
     * @return A map containing the details of the authenticated user.
     * @throws AccountException if the user is not found.
     */
    public Map<String, Object> getUserInfo() {
        Optional<UserInfoModel> userOptional = getUserByEmailFromToken();

        if (userOptional.isPresent()) {
            UserInfoModel user = userOptional.get();

            // Construction du json
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("created_at", user.getCreatedAt());
            response.put("updated_at", user.getUpdatedAt());

            return response;
        } else {
            throw new AccountException("bad token");
        }
    }

    /**
     * Retrieves the detailed information of the currently authenticated user.
     *
     * @return A map containing the details of the authenticated user.
     * @throws AccountException if the user is not found.
     */
    public Map<String, Object> getUserById(int id) {
        Optional<UserInfoModel> userId = userInfoRepository.findById(id);

        if (userId.isPresent()) {
            UserInfoModel user = userId.get();

            // Construction du json
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("created_at", user.getCreatedAt());
            response.put("updated_at", user.getUpdatedAt());

            return response;
        } else {
            throw new AccountException("User not found");
        }
    }

}
