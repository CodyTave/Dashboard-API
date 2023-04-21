package com.dashboard.App.Controllers;

import com.dashboard.App.Entities.Category;
import com.dashboard.App.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return categoryService.findAllCats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCatById(@PathVariable(value = "id" )Long CatId) {
        return categoryService.getCat(CatId);
    }
    @GetMapping("/Search")
    public ResponseEntity<List<Category>> getProdByKeyword(@RequestParam(name = "kw" ,required = false )String Keyword) {
        List<Category> categories;
       if (Keyword == null) {
            categories = getAllCategories().getBody();
        } else {
            categories = categoryService.getCatByName(Keyword);
        }
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }
    @GetMapping("/Trash")
    public ResponseEntity<List<Category>> getDeleted(){
        return categoryService.findDeleted();
    }
    @PostMapping("/New")
    public ResponseEntity<Category> createCategory(Category category) {
        return categoryService.saveCat(category);
    }
    @PatchMapping("/Edit/{id}")
    public ResponseEntity<Category> editProduct(@PathVariable(value = "id" )Long CatId,Category CatEdits){
        return categoryService.editCat(CatId,CatEdits);
    }
    @PatchMapping ("/MoveToTrash/{id}")
    public ResponseEntity<Void> moveToTrash(@PathVariable(value="id") Long CatId){
        return categoryService.MoveToTrash(CatId);
    }
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value="id") Long CatId){
        return categoryService.DeletePermanently(CatId);
    }




}
