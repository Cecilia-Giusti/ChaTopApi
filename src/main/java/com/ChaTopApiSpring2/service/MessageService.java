package com.ChaTopApiSpring2.service;

import com.ChaTopApiSpring2.dto.request.MessageRequest;
import com.ChaTopApiSpring2.exceptions.MessageException;
import com.ChaTopApiSpring2.model.MessageModel;
import com.ChaTopApiSpring2.model.RentalModel;
import com.ChaTopApiSpring2.repository.MessageRepository;
import com.ChaTopApiSpring2.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class providing message-related functionalities.
 * This service offers methods to handle message-related logic, such as adding new messages,
 * utilizing the repositories for data persistence and validation.
 */
@Service
public class MessageService {

    @Autowired
    private UserService userService;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Add a new message to the database using the provided request data.
     * This method validates the message request details, retrieves the associated rental,
     * sets the required fields, and persists the new message in the database.
     *
     * @param messageRequest The data transfer object containing message details.
     * @throws MessageException If any of the message request details are invalid or missing.
     */
    public void addMessage(MessageRequest messageRequest){
        if(messageRequest.getUser_id().isEmpty()){
            throw new MessageException("Bad user_id, message or rental_id");
        }

        if(messageRequest.getRental_id().isEmpty()){
            throw new MessageException("Bad user_id, message or rental_id");
        }

        if(messageRequest.getMessage().isEmpty()){
            throw new MessageException("Bad user_id, message or rental_id");
        }

        Optional<RentalModel> rentalOpt = rentalRepository.findById(Integer.parseInt(messageRequest.getRental_id()));
        if(rentalOpt.isEmpty()){
            throw new MessageException("Bad user_id, message or rental_id");
        }

        MessageModel newMessage = new MessageModel();

        newMessage.setUser_id(Integer.parseInt(messageRequest.getUser_id()));
        newMessage.setMessage(messageRequest.getMessage());

        RentalModel associatedRental = rentalOpt.get();
        newMessage.setRental(associatedRental);
        newMessage.setRental_id(associatedRental.getId());
        newMessage.setOwner_id(associatedRental.getOwnerId());

        messageRepository.save(newMessage);
    }
}
