package com.andres.curso.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.andres.curso.springboot.app.springboot_crud.ProductValidator;
import com.andres.curso.springboot.app.springboot_crud.entities.Product;
import com.andres.curso.springboot.app.springboot_crud.repositories.ProductRepository;
import com.andres.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.Valid;

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

    @Autowired
    private ProductValidator validator;

    ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> view(@PathVariable Integer id) {
        Optional<Product> productOptional = productService.fidById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<?> creaEntity(@Valid @RequestBody Product product, BindingResult result) {
        // Con la anotacion @Valid hacemos que acceda a las validaciones que hemos
        // colocado en el Entity
        // También se ha añadido el BindingResult. Ojo que el orden de los argumentos es
        // importante
        validator.validate(product, result);
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Product newProduct = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updaEntity(@Valid @RequestBody Product product, BindingResult result,
            @PathVariable Integer id) {
        // Con la anotacion @Valid hacemos que acceda a las validaciones que hemos
        // colocado en el Entity
        // También se ha añadido el BindingResult. Ojo que el orden de los argumentos es
        // importante
        validator.validate(product, result);
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        product.setId(id);

        Product newProduct = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Optional<Product> productOptional = productService.fidById(id);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());

            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField()
                    + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
