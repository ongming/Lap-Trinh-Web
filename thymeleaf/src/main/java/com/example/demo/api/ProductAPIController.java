package com.example.demo.api;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.model.Response;
import com.example.demo.service.IProductService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductAPIController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }

    @PostMapping(path = "/getProduct")
    public ResponseEntity<?> getProduct(@Validated @RequestParam("id") Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<Response>(new Response(true, "Thành công", product.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        Optional<Product> optProduct = productService.findByProductName(product.getProductName());
        if (optProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product đã tồn tại");
        } else {
            product.setCreateDate(new Date());
            productService.save(product);
            return new ResponseEntity<Response>(new Response(true, "Thêm Thành công", product), HttpStatus.OK);
        }
    }

    @PutMapping(path = "/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        Optional<Product> optProduct = productService.findById(product.getProductId());
        if (optProduct.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
        } else {
            productService.save(product);
            return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", product), HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@Validated @RequestParam("productId") Long productId) {
        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
        } else {
            productService.delete(optProduct.get());
            return new ResponseEntity<Response>(new Response(true, "Xóa Thành công", optProduct.get()), HttpStatus.OK);
        }
    }
}