package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.dto.request.RentalRequest;
import com.ChaTopApiSpring2.dto.response.MessageResponse;
import com.ChaTopApiSpring2.dto.response.RentalResponse;
import com.ChaTopApiSpring2.dto.response.RentalsResponse;
import com.ChaTopApiSpring2.model.RentalModel;
import com.ChaTopApiSpring2.repository.RentalRepository;
import com.ChaTopApiSpring2.service.RentalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller responsible for managing rental operations.
 */
@RestController
@Slf4j
@RequestMapping(value = "rentals")
public class RentalsController {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalService rentalService;

    /**
     * Retrieves a list of all rentals.
     *
     * @return A ResponseEntity containing a list of all rentals.
     */
    @GetMapping("")
    @ApiOperation(value = "Get full rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200,  message = "OK", response =  RentalsResponse.class)
    })
    public ResponseEntity<RentalsResponse> getFullRentals() {
        List<RentalModel> rentals = rentalRepository.findAll();
        RentalsResponse response = new RentalsResponse(rentals);
        return ResponseEntity.ok(response);
    }

    /**
     * Registers a new rental based on the provided rental details.
     *
     * @param rentalRequest The details of the new rental.
     * @param bindingResult Holds the results of the validation for the provided rental details.
     * @return A ResponseEntity containing a message indicating the outcome of the rental registration.
     * @throws IOException If there's an error during processing.
     */
    @PostMapping("")
    @ApiOperation(value = "Creates a new rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental created !", response = MessageResponse.class)
    })
    public ResponseEntity<MessageResponse> createRental(@Valid @ModelAttribute RentalRequest rentalRequest, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body((MessageResponse) errors);
        }

        rentalService.addRental(rentalRequest);

        MessageResponse response = new MessageResponse("Rental created !");
        return ResponseEntity.ok( response);
    }

    /**
     * Retrieves details of a specific rental by its ID.
     *
     * @param id The unique identifier of the rental.
     * @return A ResponseEntity wrapping the details of the specified rental.
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Get one rental with id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,  message = "OK", response =  RentalResponse.class)
    })
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable int id) {
        RentalModel rental = rentalService.getRentalById(id);

        RentalResponse response = new RentalResponse(rental);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates the details of a specific rental by its ID.
     *
     * @param id The unique identifier of the rental to be modified.
     * @param rentalRequest Contains the updated details for the rental.
     * @return A ResponseEntity conveying the outcome of the rental update operation.
     * @throws IOException If there is an issue during the update process.
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental updated !", response = MessageResponse.class)
    })
    public ResponseEntity<MessageResponse> updateRental(@PathVariable int id, @ModelAttribute RentalRequest rentalRequest) throws IOException {
        RentalModel updatedRental = rentalService.updateRental(id, rentalRequest);

        MessageResponse response = new MessageResponse("Rental updated !");
        return ResponseEntity.ok(response);
    }

}
