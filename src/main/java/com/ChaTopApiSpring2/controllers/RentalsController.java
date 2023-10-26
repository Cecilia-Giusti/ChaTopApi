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
 * Controller handling rental-related operations.
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
     * @return A ResponseEntity containing the RentalsResponse.
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
     * Creates a new rental.
     *
     * @param rentalRequest Details of the rental to be created.
     * @param bindingResult Object holding validation results.
     * @return A ResponseEntity with a confirmation message or validation errors.
     * @throws IOException If there's an I/O error.
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
     * Retrieves a rental by its ID.
     *
     * @param id The ID of the rental to retrieve.
     * @return A ResponseEntity containing details of the rental or an error message.
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Get one rental with id")
    @ApiResponses(value = {
            @ApiResponse(code = 200,  message = "OK", response =  RentalResponse.class)
    })
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable int id) {
        Map<String, Object> rental = rentalService.getRentalById(id);
        RentalResponse response = new RentalResponse((RentalModel) rental);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a rental by its ID.
     *
     * @param id The ID of the rental to update.
     * @param rentalRequest Updated details of the rental.
     * @return A ResponseEntity with a confirmation message.
     * @throws IOException If there's an I/O error.
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a rental")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Rental updated !", response = MessageResponse.class)
    })
    public ResponseEntity<MessageResponse> updateRental(@PathVariable int id, @ModelAttribute RentalRequest rentalRequest) throws IOException {

        rentalService.updateRental(id, rentalRequest);

        MessageResponse response = new MessageResponse("Rental created !");
        return ResponseEntity.ok(response);
    }


}
