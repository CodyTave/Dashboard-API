package com.dashboard.App.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id ;
    private String nom;
    private String description;
    private String image;
    private String slug;
    @ManyToOne
    @PrimaryKeyJoinColumn
    private Category category;
    private LocalDate date_creation;
    private LocalDate date_modification;
    private boolean deleted;
    private LocalDate date_deleted;


    public Product(String nom, String description , boolean deleted) {
        this.nom = nom;
        this.description = description;
        this.deleted = deleted;
    }


}
