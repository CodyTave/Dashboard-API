package com.dashboard.App.Services;

import com.dashboard.App.Entities.Category;
import com.dashboard.App.Repos.CategoryRepository;
import com.dashboard.App.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    private Utilities utilities;

    public ResponseEntity<List<Category>> findAllCats()
    {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
    public ResponseEntity<Category> getCat(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    public List<Category> getCatByName(String kw){
        return categoryRepository.findByKeyword(kw);
    }
    public ResponseEntity<Category> saveCat(Category category){
        String Slug = Utilities.toSlug(category.getNom()) ;
        LocalDate currentDate = Utilities.getCurrentDate();
        category.setSlug(Slug);
        category.setDate_creation(currentDate);
        Category savedCat = categoryRepository.save(category);
        return ResponseEntity.ok(savedCat);

    }
    public ResponseEntity<Category> editCat(Long id,Category CategoryEdit){
        Category category = getCat(id).getBody();
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        if(CategoryEdit.getNom() != null){
            category.setNom(CategoryEdit.getNom());
        }
        if(CategoryEdit.getDescription() != null){
            category.setDescription(CategoryEdit.getDescription());
        }
        category.setDeleted(CategoryEdit.isDeleted());


        Category updatedCategory = saveCat(category).getBody();
        updatedCategory.setDate_modification(Utilities.getCurrentDate());

        return ResponseEntity.ok(updatedCategory);

    }
    public ResponseEntity<List<Category>> findDeleted(){
        return ResponseEntity.ok(categoryRepository.findDeleted());
    }

    public ResponseEntity<Void> DeletePermanently(Long Id){
        Category Cat = getCat(Id).getBody();
        if (Cat == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryRepository.delete(Cat);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    public ResponseEntity<Void> MoveToTrash(Long Id){
        Category Cat = getCat(Id).getBody();
        if (Cat == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cat.setDeleted(true);
        saveCat(Cat);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
