package com.ChaTopApiSpring2.controllers;

import com.ChaTopApiSpring2.dto.request.RentalRequest;
import com.ChaTopApiSpring2.model.RentalModel;
import com.ChaTopApiSpring2.repository.RentalRepository;
import com.ChaTopApiSpring2.service.RentalService;
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
     * @return A ResponseEntity containing a list of RentalModel.
     */
    @GetMapping("")
    public ResponseEntity<List<RentalModel>> getFullRentals() {
        List<RentalModel> rentals = rentalRepository.findAll();
        return ResponseEntity.ok(rentals);
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
    public ResponseEntity<Map<String, String>> createRental(@Valid @ModelAttribute RentalRequest rentalRequest, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        rentalService.addRental(rentalRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created !");

        return ResponseEntity.ok(response);
    }


    /**
     * Retrieves a rental by its ID.
     *
     * @param id The ID of the rental to retrieve.
     * @return A ResponseEntity containing details of the rental or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(@PathVariable int id) {
        Map<String, Object> response = rentalService.getRentalById(id);
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
    public ResponseEntity<Map<String, String>> updateRental(@PathVariable int id, @ModelAttribute RentalRequest rentalRequest) throws IOException {

        rentalService.updateRental(id, rentalRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated !");
        return ResponseEntity.ok(response);
    }


}
