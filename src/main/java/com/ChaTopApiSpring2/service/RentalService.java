package com.ChaTopApiSpring2.service;

import com.ChaTopApiSpring2.dto.request.RentalRequest;
import com.ChaTopApiSpring2.exceptions.AccountException;
import com.ChaTopApiSpring2.exceptions.RentalException;
import com.ChaTopApiSpring2.model.RentalModel;
import com.ChaTopApiSpring2.model.UserInfoModel;
import com.ChaTopApiSpring2.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for managing rental-related operations.
 * This service provides methods for managing rentals, such as creating, fetching, and updating them.
 * It interfaces with the database via the rental repository and integrates with other services, such as S3Service and UserService.
 */
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserService userService;

    /**
     * Creates and persists a new rental entry in the database.
     *
     * @param rentalRequest The DTO containing the details of the rental to be created.
     * @throws IOException If there's an issue with file operations, like uploading a picture to S3.
     */
    public void addRental(RentalRequest rentalRequest) throws IOException {


        Optional<UserInfoModel> userOptional = userService.getUserByEmailFromToken();
        if (userOptional.isEmpty()) {
            throw new AccountException("User not found");
        }
        UserInfoModel user = userOptional.get();

        RentalModel newRental = new RentalModel();

        newRental.setName(String.valueOf(rentalRequest.getName()));
        newRental.setSurface(String.valueOf(rentalRequest.getSurface()));
        newRental.setPrice(String.valueOf(rentalRequest.getPrice()));


        if (rentalRequest.getPicture() != null && !rentalRequest.getPicture().isEmpty()) {
            String imageUrl = s3Service.uploadFile(rentalRequest.getPicture());
            newRental.setPicture(imageUrl);
        }

        newRental.setDescription(String.valueOf(rentalRequest.getDescription()));
        newRental.setOwnerId(user.getId());


        rentalRepository.save(newRental);
    }

    /**
     * Retrieve rental details by ID.
     *
     * @param id The rental ID to be fetched.
     * @return The details of the rental.
     * @throws RentalException If the rental is not found.
     */
    public RentalModel getRentalById(int id) {
        Optional<RentalModel> rentalOpt = rentalRepository.findById(id);

        if (rentalOpt.isPresent()) {
            return rentalOpt.get();
        } else {
            throw new RentalException("Rental not found");
        }
    }


    /**
     * Update an existing rental's details using the provided request data.
     *
     * @param id The unique identifier of the rental to be updated.
     * @param rentalRequest The DTO containing the updated rental details.
     * @return The updated rental model.
     * @throws IOException If there's an issue with file operations, like uploading a new picture to S3.
     */
    public RentalModel updateRental(int id, RentalRequest rentalRequest) throws IOException {
        Optional<RentalModel> rentalOptional = rentalRepository.findById(id);

        if (rentalOptional.isEmpty()) {
            throw new RentalException("Rental not found");
        }

        RentalModel existingRental = rentalOptional.get();
        existingRental.setName(String.valueOf(rentalRequest.getName()));
        existingRental.setSurface(String.valueOf(rentalRequest.getSurface()));
        existingRental.setPrice(String.valueOf(rentalRequest.getPrice()));
        existingRental.setDescription(String.valueOf(rentalRequest.getDescription()));

        if (rentalRequest.getPicture() != null && !rentalRequest.getPicture().isEmpty()) {
            if (existingRental.getPicture() != null && !existingRental.getPicture().isEmpty()) {
                s3Service.deleteFile(existingRental.getPicture());
            }

            String imageUrl = s3Service.uploadFile(rentalRequest.getPicture());
            existingRental.setPicture(imageUrl);
        }

        rentalRepository.save(existingRental);
        return existingRental;
    }
}
