package com.ChaTopApiSpring2.service;

import com.ChaTopApiSpring2.model.UserInfoModel;
import com.ChaTopApiSpring2.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class providing authentication related services.
 * This service provides methods to handle authentication logic,
 * such as user creation, utilizing the UserInfoRepository for data persistence.
 */
@Service
public class AuthService {

    /**
     * Autowired instance of UserInfoRepository to access and manipulate user data in the database.
     */
    @Autowired
    private UserInfoRepository userRepository;

    /**
     * Create a new user with the given details and persist in the database.
     *
     * @param name     The name of the user.
     * @param email    The email address of the user.
     * @param password The password for the user.
     * @return The UserInfoModel entity saved in the database.
     */
    public UserInfoModel createUser(String name, String email, String password) {
        UserInfoModel user = new UserInfoModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }
}
