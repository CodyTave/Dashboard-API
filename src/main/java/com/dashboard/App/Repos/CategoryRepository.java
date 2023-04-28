package com.dashboard.App.Repos;

import com.dashboard.App.Entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.nom) LIKE %:keyword%")
    Page<Category> findByKeyword(Pageable pageable,@Param("keyword") String keyword);

    @Query("SELECT c FROM Category c WHERE c.deleted=true ORDER BY c.date_creation DESC, c.id DESC  ")
    List<Category> findDeleted();
    @Query("SELECT c FROM Category c WHERE c.deleted=false ORDER BY c.date_creation DESC,c.id DESC  ")
    Page<Category> findAll(Pageable pageable);
    @Query("SELECT c FROM Category c WHERE c.deleted=false ORDER BY  c.nom ASC ")
    List<Category> findByNameOrder();

}
