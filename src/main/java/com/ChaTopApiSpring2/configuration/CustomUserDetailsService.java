package com.ChaTopApiSpring2.configuration;

import com.ChaTopApiSpring2.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom service for retrieving a user's details from its database.
 * This service is used by Spring Security to load the user's details during the authentication process.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * Loads user details based on their email address.
     *
     * @param email The email address of the user to search for.
     * @return The details of the user corresponding to the provided email address.
     * @throws UsernameNotFoundException If no user is found with the provided email address.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
