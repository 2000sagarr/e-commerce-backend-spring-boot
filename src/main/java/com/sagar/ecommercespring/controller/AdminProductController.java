package com.sagar.ecommercespring.controller;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.model.Product;
import com.sagar.ecommercespring.request.CreateProductRequest;
import com.sagar.ecommercespring.response.ApiResponse;
import com.sagar.ecommercespring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @Autowired
    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProductHandler(@RequestBody CreateProductRequest req) throws ProductException {
        Product createdProduct = productService.createProduct(req);
        return new ResponseEntity<>(createdProduct, HttpStatus.ACCEPTED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Product>> createMultipleProductsHandler(@RequestBody List<CreateProductRequest> reqs) throws ProductException {
        List<Product> createdProducts = new ArrayList<>();
        for (CreateProductRequest req : reqs) {
            Product createdProduct = productService.createProduct(req);
            createdProducts.add(createdProduct);
        }
        return new ResponseEntity<>(createdProducts, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable Long productId) throws ProductException{
        String msg=productService.deleteProduct(productId);
        ApiResponse res=new ApiResponse(msg,true);
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Product>> recentlyAddedProduct(){
        List<Product> products = productService.recentlyAddedProduct();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }


    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProductHandler(@RequestBody Product req,@PathVariable Long productId) throws ProductException{
        Product updatedProduct=productService.updateProduct(productId, req);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req) throws ProductException{
        for(CreateProductRequest product:req) {
            productService.createProduct(product);
        }
        ApiResponse res=new ApiResponse("products created successfully",true);
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }
}
