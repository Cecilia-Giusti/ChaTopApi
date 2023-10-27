package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.dto.request.MessageRequest;
import com.ChaTopApiSpring2.dto.response.MessageResponse;
import com.ChaTopApiSpring2.service.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller handling message-related operations.
 */
@RestController
@Slf4j
@RequestMapping(value = "message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Creates a new message and responds with a confirmation message.
     *
     * @param messageRequest The details of the message to be created.
     * @return A ResponseEntity with a confirmation message indicating the success of the operation.
     */
    @PostMapping("")
    @ApiOperation(value = "Creates a new message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Message send with success", response = MessageResponse.class),
            @ApiResponse(code = 401, message = "Bad token", response = String.class),
            @ApiResponse(code = 400, message = "Bad user_id, message or rental_id", response = String.class)
    })
    public ResponseEntity<MessageResponse> createMessage(@Valid @RequestBody MessageRequest messageRequest) {
        messageService.addMessage(messageRequest);

        MessageResponse response = new MessageResponse("Message send with success");
        return ResponseEntity.ok(response);
    }
}
