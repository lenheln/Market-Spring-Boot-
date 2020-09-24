package com.geekbrains.geekmarket.controllers;

import com.geekbrains.geekmarket.entities.Product;
import com.geekbrains.geekmarket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public String getAll(Model model){
        List<Product> productList = productService.findAllProducts();
        model.addAttribute("productList", productList);
        return "shop-page";
    }

    // ---------REST------------//
//    //REST - GET
//    @GetMapping("")
//    public List<Product> getProducts() {
//        return productService.findAllProducts();
//    }
//
//    //REST - POST
//    @PostMapping("/add")
//    public Product addProduct(@RequestBody Product product) {
//        product.setId(0L);
//        product = productService.saveOrUpdate(product);
//        return product;
//    }
    // ---------REST------------//


    @PostMapping("/add")
    public String showAddForm(Product product){
        productService.addProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Product product = new Product();
        product.setName("Unknown");
        product.setPrice(0d);
        model.addAttribute("product",product);
        return "addProduct";
    }

//    @GetMapping("/id")
//    public String getById(Model model, @RequestParam int id) {
//        Product product = productService.findProductById(id);
//        model.addAttribute("product",product);
//        return "product";
//    }
//
//    @GetMapping("/name")
//    public String getById(Model model, @RequestParam String name) {
//        Product product = productService.findProductByName(name);
//        model.addAttribute("product",product);
//        return "product";
//    }
//
//    @GetMapping("/maxPrice")
//    public String getByMaxPrice(Model model, @RequestParam int maxPrice) {
//        List<Product> productList = productService.findByMaxPrice(maxPrice);
//        model.addAttribute("productList", productList);
//        return "productList";
//    }
//
//    @GetMapping("/minPrice")
//    public String getByMinPrice(Model model, @RequestParam int minPrice) {
//        List<Product> productList = productService.findByMinPrice(minPrice);
//        model.addAttribute("productList", productList);
//        return "productList";
//    }
//
//    @GetMapping("/between")
//    public String getByPriceBetween(Model model,
//                                    @RequestParam int minPrice,
//                                    @RequestParam int maxPrice) {
//        List<Product> productList = productService.findByPriceBetween(minPrice, maxPrice);
//        model.addAttribute("productList", productList);
//        return "productList";
//    }
//
//    //Вывод постранично: указываем в параметрах номер страницы и выводится то, что нужно
//    @GetMapping("/page")
//    public String getByPage(Model model, @RequestParam int page) {
//        List<Product> productList = productService.findAllPage(page);
//        model.addAttribute("productList", productList);
//        return "productList";
//    }
}
