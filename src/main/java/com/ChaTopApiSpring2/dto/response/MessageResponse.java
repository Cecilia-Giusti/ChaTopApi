package com.ChaTopApiSpring2.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Represents the structure of the message response.
 */
@ApiModel(description = "Response structure for the message operation")
public class MessageResponse {

    /**
     * The actual content of the confirmation message.
     */
    @ApiModelProperty(value = "Confirmation message")
    private String message;

    /**
     * Constructs a new MessageResponse with the provided message.
     *
     * @param message The content of the confirmation message.
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Retrieves the confirmation message.
     *
     * @return The confirmation message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Updates the content of the confirmation message.
     *
     * @param message The new content for the confirmation message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
