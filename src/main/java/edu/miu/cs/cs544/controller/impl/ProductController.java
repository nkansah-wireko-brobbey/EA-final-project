package edu.miu.cs.cs544.controller.impl;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.ProductDTO;
import edu.miu.cs.cs544.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.addProduct(productDTO), HttpStatus.CREATED);
    }


    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody ProductDTO productDTO) throws CustomError {
        return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) throws CustomError {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) throws CustomError {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }


    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    //catches all the exception thrown by ProductService class and returns a response entity
    @ExceptionHandler(CustomError.class)
    public ResponseEntity<?> handleRestErrors(CustomError err) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", err.getMessage());
        return new ResponseEntity<>(map, err.getStatusCode() == null ? HttpStatus.BAD_REQUEST : err.getStatusCode());
    }
}
