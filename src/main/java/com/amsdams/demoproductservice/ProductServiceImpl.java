package com.amsdams.demoproductservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        log.info("Find product with id: {}", id);
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        log.info("Find all products");
        return productRepository.findAll();
    }

    @Override
    public boolean update(Product product) {
        log.info("Update product: {}", product);
        return productRepository.update(product);
    }

    @Override
    public Product save(Product product) {
        // Set the product version to 1 as we're adding a new product to the database
        product.setVersion(1);

        log.info("Save product to the database: {}", product);
        return productRepository.save(product);
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Delete product with id: {}", id);
        return productRepository.delete(id);
    }
}
