package com.ChaTopApiSpring2.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a message in the chat application.
 * This entity is associated with the "messages" table in the database.
 * It contains details of a message like the user who sent it, the actual message content,
 * the associated rental property, the owner of the rental, and timestamps for creation and update.
 */
@Entity
@Data
@Table(name = "messages")
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int user_id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RentalModel rental;

    private int rental_id;

    private int owner_id;

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
