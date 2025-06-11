package com.atharva.ecommerce.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
//  JPA looks at the @ManyToOne type → like User.
//  Then it looks at the primary key of the User table (like id).
//  It creates a foreign key column (like user_id) in the current table that points to User.id.
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(name = "rating")
    private double rating;

    private LocalDateTime createdAt;

}
