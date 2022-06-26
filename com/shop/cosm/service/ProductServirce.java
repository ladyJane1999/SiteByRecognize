package com.shop.cosm.service;

import com.shop.cosm.domain.Product;
import com.shop.cosm.repos.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServirce {

    final private ProductRepository productRepository;

    public ProductServirce( ProductRepository productRepository) {
        this.productRepository = productRepository;

    }
    @Cacheable("Product")
    public List<Product> getAll()

    {
        return  productRepository.findAll();
    }

    public Product getOne(Long id) {
        return productRepository.findById(id).get();
    }


    @CacheEvict(value = "Doctor", key = "#id")
    public Long delete(Long id) {

        productRepository.deleteById(id);

        return id;
    }

    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    @CachePut(value = "Product", key = "#product.id")
    public Product updateProduct(Product product) {

        return productRepository.save(product);
    }

}
