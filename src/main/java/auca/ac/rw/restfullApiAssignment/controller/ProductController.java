package auca.ac.rw.restfullApiAssignment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Product;
import auca.ac.rw.restfullApiAssignment.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")    
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @PostMapping(value = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) {
        String saveProduct = productService.addNewProduct(product);

        if(saveProduct.equals("Product added successfully")){
            return new ResponseEntity<>(saveProduct, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(saveProduct, HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/updateProduct/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        String updateResult = productService.updateProduct(id, product);
        
        if(updateResult.equals("Product updated successfully")){
            return new ResponseEntity<>(updateResult, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(updateResult, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/deleteProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        String deleteResult = productService.deleteProduct(id);
        
        if(deleteResult.equals("Product deleted successfully")){
            return new ResponseEntity<>(deleteResult, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(deleteResult, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/viewAllProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        
        if(products.isEmpty()){
            return new ResponseEntity<>("No products found", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/viewProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchProductByCategory(@RequestParam  String category){

        List<Product> getProducts = productService.searchByCategory(category);
        if(getProducts !=null){
            return new ResponseEntity<>(getProducts, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("Product with that category not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/searchByPriceAndBrand", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchProductByPriceAndBrand(@RequestParam Double price, @RequestParam String brand){
        List<Product> getProducts = productService.searchByPriceAndBrand(price, brand);
        if(getProducts != null){
            return new ResponseEntity<>(getProducts, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("Product with that price and brand not found", HttpStatus.NOT_FOUND);
        }
    }

}
