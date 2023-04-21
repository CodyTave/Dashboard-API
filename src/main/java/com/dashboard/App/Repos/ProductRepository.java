package com.dashboard.App.Repos;

import com.dashboard.App.Entities.Category;
import com.dashboard.App.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.nom) LIKE %:keyword%")
    List<Product> findByKeyword(@Param("keyword") String keyword);
    @Query("SELECT p FROM Product p WHERE p.deleted=true ")
    List<Product> findDeleted();
    @Query("SELECT p FROM Product p WHERE p.deleted=false ")
    List<Product> findAll();

}
