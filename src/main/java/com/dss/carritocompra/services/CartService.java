package com.dss.carritocompra.services;

import com.dss.carritocompra.domain.ProductRepository;
import com.dss.carritocompra.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final List<Product> productsOnCart = new ArrayList<>();

    public void addProduct(Product product) {
        if (productsOnCart.contains(product)) {
            throw new IllegalArgumentException("Product already on cart");
        }
        productsOnCart.add(product);
    }

    public void deleteProduct(Long id) {
        productsOnCart.removeIf(product -> product.getId().equals(id));
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        productsOnCart.removeIf(product -> !products.contains(product));

        return productsOnCart;
    }
}
