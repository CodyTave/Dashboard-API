package com.dashboard.App.Controllers;

import com.dashboard.App.Entities.Product;
import com.dashboard.App.Services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private  ProductsService productsService;
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return productsService.findAllProds();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProdById(@PathVariable(value = "id" )Long prodId) {
        return productsService.getProd(prodId);
    }
    @GetMapping("/Search")
    public ResponseEntity<List<Product>> getProdByKeyword(@RequestParam(name = "kw" ,required = false )String Keyword) {
        List<Product> products;
        if (Keyword == null) {
            products = productsService.findAllProds().getBody();
        } else {
            products = productsService.getProdByName(Keyword);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
    @GetMapping("/Trash")
    public ResponseEntity<List<Product>> getDeleted(){
        return productsService.findDeleted();
    }
    @PostMapping()
    public ResponseEntity<Product> createProduct(Product product) {
        return productsService.saveProd(product);
    }
    @PatchMapping("/{id}")
        public ResponseEntity<Product> editProduct(@PathVariable(value = "id" )Long prodId,Product prodEdits){
            return productsService.editProd(prodId,prodEdits);
        }
    @PatchMapping ("/Delete/{id}")
    public ResponseEntity<Void> moveToTrash(@PathVariable(value="id") Long ProdId){
        return productsService.MoveToTrash(ProdId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value="id") Long ProdId){
        return productsService.DeletePermanently(ProdId);
    }

}

