package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    @Query(value = """
    SELECT c.* FROM cards c
    JOIN users u ON u.id = c.owner_id
    WHERE u.email = :email
    """, nativeQuery = true)
    List<Card> findAllClientCards(@Param("email") String email);

    @Query(value = """
    SELECT SUM(c.balance) FROM cards c
    JOIN users u ON u.id = c.owner_id
    WHERE u.email = :email
    """, nativeQuery = true)
    Long getClientBalance(@Param("email") String email);

    @Query(value = """
    SELECT c.* FROM cards c
    JOIN users u ON u.id = c.owner_id
    WHERE u.email = :email AND c.card_number = :card
    """, nativeQuery = true)
    Optional<Card> findByCardNumberToUserWithSuchEmail(@Param("email") String email,@Param("card") String cardNumber);

}
