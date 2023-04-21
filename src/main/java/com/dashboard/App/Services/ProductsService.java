package com.dashboard.App.Services;

import com.dashboard.App.Entities.Category;
import com.dashboard.App.Entities.Product;
import com.dashboard.App.Repos.ProductRepository;
import com.dashboard.App.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ProductsService {
    @Autowired
    private ProductRepository productRepository;
    private Utilities utilities;
    public ResponseEntity<List<Product>> findAllProds()
    {
        return ResponseEntity.ok(productRepository.findAll());
    }
    public ResponseEntity<Product> getProd(Long id){
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

}
    public List<Product> getProdByName(String kw){
        return productRepository.findByKeyword(kw);
    }
    public ResponseEntity<Product> saveProd(Product prod){
        String Slug = Utilities.toSlug(prod.getNom()) ;
        LocalDate currentDate = Utilities.getCurrentDate();
        prod.setSlug(Slug);
        prod.setDate_creation(currentDate);
        System.out.println(prod);
        Product savedProd = productRepository.save(prod);
        return ResponseEntity.ok(savedProd);

    }
    public ResponseEntity<Product> editProd(Long id,Product productEdit){
        Product product = getProd(id).getBody();
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        if(productEdit.getNom() != null){
            product.setNom(productEdit.getNom());
        }
        if(productEdit.getDescription() != null){
            product.setDescription(productEdit.getDescription());
        }
        if(productEdit.getCategory() != null){
            product.setCategory(productEdit.getCategory());
        }
        product.setDeleted(productEdit.isDeleted());

        Product updatedProduct = saveProd(product).getBody();
        updatedProduct.setDate_modification(Utilities.getCurrentDate());

        return ResponseEntity.ok(updatedProduct);

    }
    public ResponseEntity<List<Product>> findDeleted(){
        return ResponseEntity.ok(productRepository.findDeleted());
    }

    public ResponseEntity<Void> DeletePermanently(Long Id){
        Product Prod = getProd(Id).getBody();
        if (Prod == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productRepository.delete(Prod);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    public ResponseEntity<Void> MoveToTrash(Long Id){
        Product Prod = getProd(Id).getBody();
        if (Prod == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Prod.setDeleted(true);
        saveProd(Prod);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

