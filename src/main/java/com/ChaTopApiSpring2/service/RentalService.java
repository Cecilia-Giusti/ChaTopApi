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
 * Service class providing rental-related functionalities.
 * This service offers methods to handle rental-related logic, such as adding, updating, and retrieving rentals,
 * utilizing the repositories for data persistence and validation.
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
     * Add a new rental to the database using the provided request data.
     *
     * @param rentalRequest The data transfer object containing rental details.
     * @throws IOException If there's an error during file operations (e.g., uploading to S3).
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
     * @return A map containing rental details.
     * @throws RentalException If the rental is not found.
     */
    public Map<String, Object> getRentalById(int id) {
        Optional<RentalModel> rentalId = rentalRepository.findById(id);

        if (rentalId.isPresent()) {
            RentalModel rental = rentalId.get();

            // Construction du json
            Map<String, Object> response = new HashMap<>();
            response.put("id", rental.getId());
            response.put("name", rental.getName());
            response.put("surface", rental.getSurface());
            response.put("price", rental.getPrice());
            response.put("picture", rental.getPicture());
            response.put("description", rental.getDescription());
            response.put("owner_id", rental.getOwnerId());
            response.put("created_at", rental.getCreatedAt());
            response.put("updated_at", rental.getUpdatedAt());

            return response;
        } else {
            throw new RentalException("Rental not found");
        }
    }

    /**
     * Update an existing rental's details using the provided request data.
     *
     * @param id The ID of the rental to be updated.
     * @param rentalRequest The data transfer object containing the updated rental details.
     * @throws IOException If there's an error during file operations (e.g., uploading to S3).
     */
    public void updateRental(int id, RentalRequest rentalRequest) throws IOException {
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
    }

}
