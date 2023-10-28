package com.ChaTopApiSpring2.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Data Transfer Object (DTO) for creating or updating a rental.
 */
@Data
@ApiModel(description = "Data Transfer Object for creating or updating a rental.")
public class RentalRequest {

    @ApiModelProperty(value = "The name of the rental.", required = true)
    @NotNull(message = "Name cannot be empty")
    private String name;

    @ApiModelProperty(value = "The surface area of the rental.", required = true)
    @NotNull(message = "Surface cannot be empty")
    private String surface;

    @ApiModelProperty(value = "The price of the rental. Must be a positive number or zero.", required = true)
    @NotNull(message = "Price cannot be empty")
    @PositiveOrZero(message = "Price must be positive or zero")
    private String price;

    @ApiModelProperty(value = "The picture/image associated with the rental.", required = true)
    @NotNull(message = "Picture cannot be null")
    private MultipartFile picture;

    @ApiModelProperty(value = "The description of the rental.", required = true)
    @NotNull(message = "Description cannot be empty")
    private String description;
}
