package com.dss.carritocompra.controller;

import com.dss.carritocompra.entities.Product;
import com.dss.carritocompra.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@NoArgsConstructor
class ProductRequest {
    private String name;
    private double price;
}

@RestController()
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public List<Product> getProducts() {
         return productService.getAllProducts();
    }

    @GetMapping("/search{searchString}")
    public List<Product> getSelectedProducts(@PathVariable String searchString) {
        return productService.getSelectedProducts(searchString);
    }

    @GetMapping("/get/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProductById((long) id);
    }

    @PostMapping("/add")
    public List<Product> addProduct(@RequestBody ProductRequest product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        productService.saveProduct(product.getName(), product.getPrice());
        return productService.getAllProducts();
    }


    @PutMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") String id, @RequestParam String name, @RequestParam double price) {
        return "products";
    }

    @GetMapping("/delete/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct((long) id);
    }

    public void editProduct(int id, String name, double price) {
        productService.editProduct(id, name, price);
    }

    @GetMapping("/export")
    public void exportDatabase() {

    }
}
