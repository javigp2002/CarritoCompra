package com.dss.carritocompra.controller;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class WebController {
    private final ProductController productController;
    private final CartController cartController;

    private static final String PRODUCTS = "products";
    private static final String ADMIN = "admin";
    private static final String EDIT_PRODUCT = "edit-product";
    private static final String CART = "cart";

    private static final String REDIRECT = "redirect:/";

   @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("products", productController.getProducts());
        return PRODUCTS;
    }

    @RequestMapping(value = "/admin")
    public String admin(Model model) {
        model.addAttribute("products", productController.getProducts());
        return ADMIN;
    }

    @RequestMapping(value = "/products")
    public String products(Model model, @RequestParam(required = false) String searchString) {
       if (searchString != null && !searchString.isEmpty())
        model.addAttribute("products", productController.getSelectedProducts(searchString));
       else
        model.addAttribute("products", productController.getProducts());

       model.addAttribute("cart", cartController.getAllProducts());
        return PRODUCTS;
    }

    @RequestMapping(value = "/cart")
    public String getCartProducts(Model model) {
        model.addAttribute("products", cartController.getAllProducts());
        return CART;
    }

    @RequestMapping(value = "/edit-product")
    public String editProductHtml(Model model, @RequestParam int id) {
        model.addAttribute("product", productController.getProduct(id));
        return EDIT_PRODUCT;
    }


    // products
    @PostMapping("/product/add")
    public String addProduct(@RequestParam String name, @RequestParam double price) {
        productController.addProduct(name, price);
        return REDIRECT + ADMIN;
    }

    @PostMapping("/product/edit")
    public String editProduct(@RequestParam int id, @RequestParam String name, @RequestParam double price) {
        productController.editProduct(id, name, price);
        return REDIRECT + ADMIN;
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productController.deleteProduct(id);
        return REDIRECT + ADMIN;
    }

    // cart
    @PostMapping("/cartProduct/add")
    public String addProductToCart(@RequestParam int id) {
        cartController.addProduct(id);
        return REDIRECT + PRODUCTS;
    }

    @GetMapping("/cartProduct/delete/{id}")
    public String deleteCartProduct(@PathVariable int id) {
        cartController.deleteProduct((long) id);
        return REDIRECT + CART;
    }

    @GetMapping("/cartProduct/export")
    public ResponseEntity<byte[]> exportDatabase() throws DocumentException {
        byte[] ticket = cartController.exportProducts();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(ticket.length);

        return new ResponseEntity<>(ticket, headers, HttpStatus.OK);
    }

    //LOGIN
    @RequestMapping(value = "/logout")
    public String logout() {
        return "login";
    }

    @GetMapping(value = "/login")
    public String loginPost(Model model) {

        return "login";
    }


}