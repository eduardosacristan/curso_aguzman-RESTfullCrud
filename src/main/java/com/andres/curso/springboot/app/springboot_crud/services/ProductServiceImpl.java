package com.andres.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andres.curso.springboot.app.springboot_crud.entities.Product;
import com.andres.curso.springboot.app.springboot_crud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRespository;

    @Override
    @Transactional (readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productRespository.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<Product> fidById(Integer id) {
        return productRespository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRespository.save(product);
    }

    @Override
    public Optional<Product> delete(Product product) {
        Optional<Product> productOptional = productRespository.findById(product.getId());
        productOptional.ifPresent(prod -> {
            productRespository.delete(product);
        });

        return productOptional;
    }

}
