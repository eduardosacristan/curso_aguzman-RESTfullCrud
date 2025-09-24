package com.andres.curso.springboot.app.springboot_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.andres.curso.springboot.app.springboot_crud.entities.Product;
import com.andres.curso.springboot.app.springboot_crud.repositories.ProductRepository;
import com.andres.curso.springboot.app.springboot_crud.services.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public List<Product> list() {
        return productService.findAll();
    }
       
    @GetMapping("/{id}")
    public ResponseEntity<Product> view(@PathVariable Integer id) {
        Optional<Product> productOptional = productService.fidById(id);

        if(productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Product> creaEntity(@RequestBody Product product) {
        Product newProduct = productService.save(product);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updaEntity(@PathVariable Integer id, @RequestBody Product product) {
        product.setId(id);
        Product newProduct = productService.save(product);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Product> productOptional = productService.fidById(id);
        if(productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
           
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }
    
}
