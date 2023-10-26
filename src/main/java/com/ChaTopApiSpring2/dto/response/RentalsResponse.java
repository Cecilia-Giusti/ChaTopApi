package com.ChaTopApiSpring2.dto.response;

import com.ChaTopApiSpring2.model.RentalModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Represents the structure of the response returned when fetching rentals.
 */
@ApiModel(description = "Response structure for the rentals operation")
public class RentalsResponse {

    /**
     * List of rentals returned in the response.
     */
    @ApiModelProperty(value = "List of rentals")
    private List<RentalModel> rentals;

    /**
     * Constructs a RentalsResponse with the provided list of rentals.
     *
     * @param rentals List of RentalModel objects.
     */
    public RentalsResponse(List<RentalModel> rentals) {
        this.rentals = rentals;
    }

    /**
     * Retrieves the list of rentals.
     *
     * @return A list of RentalModel objects.
     */
    public List<RentalModel> getRentals() {
        return rentals;
    }

    /**
     * Sets the list of rentals.
     *
     * @param rentals A list of RentalModel objects.
     */
    public void setRentals(List<RentalModel> rentals) {
        this.rentals = rentals;
    }
}
