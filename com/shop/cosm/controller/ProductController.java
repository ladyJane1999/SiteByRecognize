package com.shop.cosm.controller;
import com.shop.cosm.domain.Product;
import com.shop.cosm.service.ProductServirce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductServirce productServirce;

    public ProductController(ProductServirce productServirce) {
        this.productServirce = productServirce;
    }


    @GetMapping
    public ResponseEntity getAllBy() {

        return  ResponseEntity.ok(productServirce.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity getOne(@PathVariable Long id) {

        return ResponseEntity.ok(productServirce.getOne(id));
    }

    @PostMapping
    public ResponseEntity addProduct( @RequestBody Product product) throws URISyntaxException {

        try {
            return ResponseEntity.created(new URI("/product/" + product.getId()))
                    .body(productServirce.addProduct(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can't create ");
        }
    }


    @PutMapping
    public ResponseEntity updateDoctor( @RequestBody Product product) throws URISyntaxException {
        return ResponseEntity.ok(productServirce.updateProduct(product));
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteDoctor(@PathVariable Long id) {

        return ResponseEntity.ok(productServirce.delete(id));
    }

}
