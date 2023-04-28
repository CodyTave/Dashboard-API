package com.dashboard.App.Repos;

import com.dashboard.App.Entities.Category;
import com.dashboard.App.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.nom) LIKE %:keyword%")
    Page<Product> findByKeyword(Pageable pageable,@Param("keyword") String keyword);
    @Query("SELECT p FROM Product p WHERE p.deleted=true ORDER BY p.date_creation DESC, p.id DESC ")
    List<Product> findDeleted();
    @Query("SELECT p FROM Product p WHERE p.deleted=false ORDER BY p.date_creation DESC, p.id DESC ")
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.deleted=false ORDER BY  p.nom ASC ")
    List<Product> findByNameOrder();

}
