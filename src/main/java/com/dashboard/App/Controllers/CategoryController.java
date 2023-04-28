package com.dashboard.App.Controllers;

import com.dashboard.App.Entities.Category;
import com.dashboard.App.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(@RequestParam(name = "az" ,required = false ,defaultValue = "false")Boolean azOrder,
                                                           @RequestParam(name = "kw" ,required = false )String Keyword,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if(Keyword != null){
            return categoryService.getCatByName(Keyword, pageable);
        }
//        if(azOrder){
//            return categoryService.findAllByAlphaOrder();
//        }
        return categoryService.findAllCats(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCatById(@PathVariable(value = "id" )Long CatId) {
        return categoryService.getCat(CatId);
    }
//    @GetMapping("/Search")
//    public ResponseEntity<List<Category>> getProdByKeyword(@RequestParam(name = "kw" ,required = false )String Keyword) {
//        List<Category> categories;
//       if (Keyword == null) {
//            categories = categoryService.findAllCats().getBody();
//        } else {
//            categories = categoryService.getCatByName(Keyword).getBody();
//        }
//        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
//    }
    @GetMapping("/Trash")
    public ResponseEntity<List<Category>> getDeleted(){
        return categoryService.findDeleted();
    }
    @PostMapping("")
    public ResponseEntity<Category> createCategory(Category category) {
        return categoryService.saveCat(category);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Category> editProduct(@PathVariable(value = "id" )Long CatId,Category CatEdits){
        return categoryService.editCat(CatId,CatEdits);
    }
    @PatchMapping ("/Delete/{id}")
    public ResponseEntity<Void> moveToTrash(@PathVariable(value="id") Long CatId){
        return categoryService.MoveToTrash(CatId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value="id") Long CatId){
        return categoryService.DeletePermanently(CatId);
    }




}
