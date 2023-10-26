package com.ChaTopApiSpring2.repository;

import com.ChaTopApiSpring2.model.RentalModel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

/**
 * Repository interface for CRUD operations on RentalModel entities.
 * This interface extends JpaRepository and specifies RentalModel as the entity type and Integer as the ID type.
 * It allows the application to perform common database operations on the "rentals" table without writing custom code.
 * Additional methods can be defined to provide more specific queries on the rentals data.
 */
public interface RentalRepository extends JpaRepository<RentalModel, Integer> {

    /**
     * Retrieve a RentalModel entity by its ID.
     * @param id The ID of the rental to be retrieved.
     * @return The RentalModel entity corresponding to the given ID.
     */
    @NotNull RentalModel getById(@NotNull Integer id);
}
