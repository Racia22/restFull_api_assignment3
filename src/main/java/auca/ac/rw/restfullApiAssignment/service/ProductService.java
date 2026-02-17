package auca.ac.rw.restfullApiAssignment.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Product;
import auca.ac.rw.restfullApiAssignment.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;


    public String addNewProduct(Product product) {
      Optional<Product> existProduct = productRepository.findById(product.getId());
      if(existProduct.isPresent()) {
        return "Product with id " + product.getId() + " already exists";
      }else{
              productRepository.save(product);

        return "Product added successfully";
      }
    }

    public String updateProduct(Long id, Product product) {
        Optional<Product> existProduct = productRepository.findById(id);
        if(existProduct.isPresent()) {
            product.setId(id); // Ensure the ID matches
            productRepository.save(product);
            return "Product updated successfully";
        }else{
            return "Product with id " + id + " not found";
        }
    }

    public String deleteProduct(Long id) {
        Optional<Product> existProduct = productRepository.findById(id);
        if(existProduct.isPresent()) {
            productRepository.deleteById(id);
            return "Product deleted successfully";
        }else{
            return "Product with id " + id + " not found";
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return product.get();
        }else{
            return null;
        }
    }

    public List<Product> searchByCategory(String categoryName){
        List<Product> products = productRepository.findByCategory(categoryName);

        if (products != null && !products.isEmpty()) {
            return products;
        }else{
            return null;
        }
    }

    public List<Product> searchByPriceAndBrand(Double price, String brand){
        List<Product> products = productRepository.findByPriceAndBrand(price, brand);

        if (products != null && !products.isEmpty()) {
            return products;
        }else{
            return null;
        }
    }
}
