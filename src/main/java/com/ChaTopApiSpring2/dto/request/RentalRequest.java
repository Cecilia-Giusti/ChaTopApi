package com.ChaTopApiSpring2.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Data Transfer Object (DTO) for creating or updating a rental.
 */
@Data
public class RentalRequest {

    /**
     * The name of the rental.
     * This field is mandatory.
     */
    @NotNull(message = "Name cannot be empty")
    private String name;

    /**
     * The surface area of the rental.
     * This field is mandatory.
     */
    @NotNull(message = "Surface cannot be empty")
    private String surface;

    /**
     * The price of the rental.
     * This field is mandatory and must be a positive number or zero.
     */
    @NotNull(message = "Price cannot be empty")
    @PositiveOrZero(message = "Price must be positive or zero")
    private String price;

    /**
     * The picture/image associated with the rental.
     * This field is mandatory.
     */
    @NotNull(message = "Picture cannot be null")
    private MultipartFile picture;

    /**
     * The description of the rental.
     * This field is mandatory.
     */
    @NotNull(message = "Description cannot be empty")
    private String description;
}
