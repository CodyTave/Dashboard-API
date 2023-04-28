package com.dashboard.App.Controllers;

import com.dashboard.App.Entities.Product;
import com.dashboard.App.Services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private  ProductsService productsService;
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(name = "az" ,required = false ,defaultValue = "false")Boolean azOrder,
                                                        @RequestParam(name = "kw" ,required = false )String Keyword,
                                                        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "10") int size
                                                        ) {
        Pageable pageable = PageRequest.of(page, size);

        if(Keyword != null){
            return productsService.getProdByName(Keyword, pageable);

        }
//        if(azOrder){
//            return productsService.findAllByAlphaOrder();
//        }
        return productsService.findAllProds(pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProdById(@PathVariable(value = "id" )Long prodId) {
        return productsService.getProd(prodId);
    }
//    @GetMapping("/Search")
//    public ResponseEntity<List<Product>> getProdByKeyword(@RequestParam(name = "kw" ,required = false )String Keyword) {
//        List<Product> products;
//        if (Keyword == null) {
//            products = productsService.findAllProds().getBody();
//        } else {
//            products = productsService.findAllProds().getBody();
//        }
//        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
//    }
    @GetMapping("/Trash")
    public ResponseEntity<List<Product>> getDeleted(){
        return productsService.findDeleted();
    }
    @PostMapping()
    public ResponseEntity<Product> createProduct(Product product,@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        return productsService.saveProd(product,file);
    }
    @PatchMapping("/{id}")
        public ResponseEntity<Product> editProduct(@PathVariable(value = "id" )Long prodId,Product prodEdits,@RequestParam(value = "file",required = false) MultipartFile file) throws IOException{
            return productsService.editProd(prodId,prodEdits,file);
        }
    @PatchMapping ("/Delete/{id}")
    public ResponseEntity<Void> moveToTrash(@PathVariable(value="id") Long ProdId){
        return productsService.MoveToTrash(ProdId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value="id") Long ProdId) throws IOException{
        return productsService.DeletePermanently(ProdId);
    }

}

