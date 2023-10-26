package com.ChaTopApiSpring2.repository;

import com.ChaTopApiSpring2.model.UserInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Repository interface for CRUD operations on UserInfoModel entities.
 * This interface extends JpaRepository and specifies UserInfoModel as the entity type and Integer as the ID type.
 * It provides standard CRUD operations on the "users" table, plus additional custom methods.
 * Additional methods, such as findByEmail, allow for more specific queries based on user information.
 */
public interface UserInfoRepository extends JpaRepository<UserInfoModel, Integer> {

    /**
     * Retrieve a UserInfoModel entity by its email.
     * @param email The email of the user to be retrieved.
     * @return An Optional containing the UserInfoModel entity if found, or empty if not found.
     */
    Optional<UserInfoModel> findByEmail(String email);

    /**
     * Retrieve a UserInfoModel entity by its ID.
     * @param id The ID of the user to be retrieved.
     * @return The UserInfoModel entity corresponding to the given ID.
     */
    @NotNull UserInfoModel getById(@NotNull Integer id);
}
