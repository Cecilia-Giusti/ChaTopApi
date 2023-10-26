package com.ChaTopApiSpring2.dto.request;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for sending messages.
 */
@Data
public class MessageRequest {

    /**
     * The ID of the user sending the message.
     * This field is mandatory.
     */
    @NotNull(message = "User ID is required")
    private String user_id;

    /**
     * The content of the message being sent.
     * This field is mandatory and should contain between 1 and 500 characters.
     */
    @NotNull(message = "Message content is required")
    @Size(min = 1, max = 500, message = "Message should be between 1 and 500 characters")
    private String message;

    /**
     * The ID of the rental associated with the message.
     * This field is mandatory.
     */
    @NotNull(message = "Rental ID is required")
    private String rental_id;
}
