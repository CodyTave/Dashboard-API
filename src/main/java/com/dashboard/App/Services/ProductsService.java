package com.dashboard.App.Services;

import com.dashboard.App.Entities.Product;
import com.dashboard.App.Repos.ProductRepository;
import com.dashboard.App.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductsService {
    @Autowired
    private ProductRepository productRepository;
    private Utilities utilities;
    public ResponseEntity<Page<Product>> findAllProds(Pageable pageable)
    {
        Page<Product> products = productRepository.findAll(pageable);
        return ResponseEntity.ok(products);
    }
    public ResponseEntity<List<Product>> findAllByAlphaOrder()
    {
        return ResponseEntity.ok(productRepository.findByNameOrder());
    }
    public ResponseEntity<Product> getProd(Long id){
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

}
    public ResponseEntity<Page<Product>> getProdByName(String kw,Pageable pageable){

        return ResponseEntity.ok(productRepository.findByKeyword(pageable, kw));
    }
    public ResponseEntity<Product> saveProd(Product prod, MultipartFile file) throws IOException {
        String Slug = Utilities.toSlug(prod.getNom()) ;
        LocalDate currentDate = Utilities.getCurrentDate();
        prod.setSlug(Slug);
        prod.setDate_creation(currentDate);
        uploadHandler(prod, file);
        Product savedProd = productRepository.save(prod);
        return ResponseEntity.ok(savedProd);

    }

    private void uploadHandler(Product prod, MultipartFile file) throws IOException {
        if (file != null) {
            String imageFileName = "product_" + UUID.randomUUID() + ".jpg";
            Path imagePath = Paths.get("C:\\Users\\Owner\\Desktop\\Dashboard FE\\Dashboard\\src\\assets\\" + imageFileName);
            Files.createDirectories(imagePath.getParent());
            file.transferTo(imagePath);
            prod.setImage("/src/assets/" + imageFileName);
        }

    }
    private void ImageDeleter(Product prod) throws IOException {
        if (prod.getImage() != null) {
            Path imagePath = Paths.get("C:\\Users\\Owner\\Desktop\\Dashboard FE\\Dashboard" + prod.getImage());
            Files.deleteIfExists(imagePath);
            prod.setImage(null);
        }
    }

    public ResponseEntity<Product> editProd(Long id,Product productEdit, MultipartFile file) throws IOException{
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
        }if(file != null){
            ImageDeleter(product);
            uploadHandler(productEdit,file);
            product.setImage(productEdit.getImage());
        }
        product.setDeleted(productEdit.isDeleted());
        product.setDate_modification(Utilities.getCurrentDate());
        String Slug = Utilities.toSlug(product.getNom()) ;
        product.setSlug(Slug);
        Product updatedProduct = productRepository.save(product) ;

        return ResponseEntity.ok(updatedProduct);

    }
    public ResponseEntity<List<Product>> findDeleted(){
        return ResponseEntity.ok(productRepository.findDeleted());
    }

    public ResponseEntity<Void> DeletePermanently(Long Id) throws IOException {
        Product Prod = getProd(Id).getBody();
        if (Prod == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ImageDeleter(Prod);
        productRepository.delete(Prod);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    public ResponseEntity<Void> MoveToTrash(Long Id){
        Product Prod = getProd(Id).getBody();
        if (Prod == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Prod.setDeleted(true);
        Prod.setDate_deleted(Utilities.getCurrentDate());

        productRepository.save(Prod);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

