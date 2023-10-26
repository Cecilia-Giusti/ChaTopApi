package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.dto.request.MessageRequest;
import com.ChaTopApiSpring2.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> createMessage(@Valid @RequestBody MessageRequest messageRequest) {
        messageService.addMessage(messageRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Message send with success");

        return ResponseEntity.ok(response);
    }
}
