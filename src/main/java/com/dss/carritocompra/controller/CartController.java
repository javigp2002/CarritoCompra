package com.dss.carritocompra.controller;

import com.dss.carritocompra.entities.Product;
import com.dss.carritocompra.services.CartService;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/cart")
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

    @PostMapping("/delete")
    public void deleteCartProduct(@RequestParam Long id) {
        cartService.deleteProduct(id);
    }

    @GetMapping("/deleteAll")
    public void deleteAllProducts() {
        cartService.deleteAllProducts();
    }

    @PostMapping("/buy")
    public void buy() {
        cartService.deleteAllProducts();
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return cartService.getAllProducts();
    }

    @GetMapping("/exportCart")
    public byte[] exportProducts() throws DocumentException {
        return cartService.exportCartProductsToPDF();
    }
}
