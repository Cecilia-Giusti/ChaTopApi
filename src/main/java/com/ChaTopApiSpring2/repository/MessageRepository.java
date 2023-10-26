package com.ChaTopApiSpring2.repository;

import com.ChaTopApiSpring2.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for CRUD operations on MessageModel entities.
 * This interface extends JpaRepository and specifies MessageModel as the entity type and Integer as the ID type.
 * It allows the application to perform common database operations on the "messages" table without writing custom code.
 */
public interface MessageRepository extends JpaRepository<MessageModel, Integer> {
}
