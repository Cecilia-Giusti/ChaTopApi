
package com.ChaTopApiSpring2.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO representing the response containing a token.
 */
@Getter
@Setter
public class TokenResponse {

    private String token;

    /**
     * Default constructor.
     */
    public TokenResponse() {
    }

    /**
     * Constructor to create an instance with a specific token.
     *
     * @param token The token string.
     */
    public TokenResponse(String token) {
        this.token = token;
    }
}
