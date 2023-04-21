package com.dashboard.App.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id ;
    private String nom;
    private String description;
    private String slug;
    private LocalDate date_creation;
    private LocalDate date_modification;
    private boolean deleted;

    public Category(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

}
