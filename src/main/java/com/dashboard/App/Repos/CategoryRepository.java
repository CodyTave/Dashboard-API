package com.dashboard.App.Repos;

import com.dashboard.App.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.nom) LIKE %:keyword%")
    List<Category> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM Category c WHERE c.deleted=true ")
    List<Category> findDeleted();
    @Query("SELECT c FROM Category c WHERE c.deleted=false ")
    List<Category> findAll();

}
