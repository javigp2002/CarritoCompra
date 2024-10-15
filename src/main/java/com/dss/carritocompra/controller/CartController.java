package com.dss.carritocompra.controller;

import com.dss.carritocompra.entities.Product;
import com.dss.carritocompra.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductController productController;

    @PostMapping("/add")
    public void addProduct(@RequestParam int id) {
        Product product = productController.getProduct(id);
        cartService.addProduct(product);
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        cartService.deleteProduct(id);
        return "redirect:/cart";
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return cartService.getAllProducts();
    }
}
