package com.andres.curso.springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import com.andres.curso.springboot.app.springbootcrud.entities.Product;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> fidById(Integer id);
    Product save(Product product);
    Optional<Product> delete(Product product);

}
