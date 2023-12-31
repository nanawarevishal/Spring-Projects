package com.zosh.ecommersyoutube.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Request.CreateProductRequest;
import com.zosh.ecommersyoutube.Response.ApiResponse;
import com.zosh.ecommersyoutube.Service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product>createProduct(@RequestBody CreateProductRequest request){
        
        Product product = productService.createProduct(request);

        return new ResponseEntity<Product>(product,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId)throws ProductException{

        productService.deleteProduct(productId);

        ApiResponse response = new ApiResponse();
        response.setMsg("Product deleted successfully...!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>>findAll(){
        
        List<Product>products = productService.findAllProducts();

        return new ResponseEntity<List<Product>>(products,HttpStatus.FOUND);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product>updateProduct(@RequestBody Product request,@PathVariable("productId")Long ProductId)throws ProductException{

        Product product = productService.updateProduct(ProductId, request);

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PostMapping("/created")
    public ResponseEntity<ApiResponse>createMultipleProduct(@RequestBody CreateProductRequest[] request){

        for(CreateProductRequest product : request){
            productService.createProduct(product);
        }

        ApiResponse response = new ApiResponse();
        response.setMsg("Products Created Successfully...!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
    }
}
