package com.dss.carritocompra.services;

import com.dss.carritocompra.domain.ProductRepository;
import com.dss.carritocompra.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ProductService {
    final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public void saveProduct(String name, double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getSelectedProducts(String searchString) {
        List<Product> allProducts = productRepository.findAll();
        if (searchString == null || searchString.isEmpty()) {
            return allProducts;
        }

        return allProducts.stream()
                .filter(product -> product.getName().contains(searchString))
                .toList();
    }

    public void editProduct(int id, String name, double price) {
        Product product = getProductById((long) id);
        product.setName(name);
        product.setPrice(price);
        productRepository.save(product);
    }
}
