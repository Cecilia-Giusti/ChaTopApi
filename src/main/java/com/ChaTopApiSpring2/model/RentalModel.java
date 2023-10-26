package com.ChaTopApiSpring2.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Represents a rental property in the application.
 * This entity is associated with the "rentals" table in the database.
 * It contains details about a rental property like its name, surface area,
 * price, picture, description, the owner, and timestamps for creation and update.
 */
@Entity
@Data
@Table(name = "rentals")
public class RentalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;


    private String surface;


    private String price;


    private String picture;


    private String description;

    private int ownerId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
