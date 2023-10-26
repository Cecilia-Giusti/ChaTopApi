package com.ChaTopApiSpring2.dto.response;

import com.ChaTopApiSpring2.model.RentalModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Represents the structure of the response returned when fetching a single rental by its ID.
 */
@ApiModel(description = "Response structure for a single rental operation")
public class RentalResponse {

    /**
     * The rental returned in the response.
     */
    @ApiModelProperty(value = "The details of a single rental")
    private RentalModel rental;

    /**
     * Constructs a RentalResponse with the provided rental.
     *
     * @param rental A RentalModel object.
     */
    public RentalResponse(RentalModel rental) {
        this.rental = rental;
    }

    /**
     * Retrieves the rental.
     *
     * @return A RentalModel object.
     */
    public RentalModel getRental() {
        return rental;
    }

    /**
     * Sets the rental.
     *
     * @param rental A RentalModel object.
     */
    public void setRental(RentalModel rental) {
        this.rental = rental;
    }
}
