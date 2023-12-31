package com.zosh.ecommersyoutube.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    //https://127.0.0.1:8080/api/products?category=cate&color=value&size=
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryhandler(@RequestParam String category,
    @RequestParam List<String>color,@RequestParam List<String> size,@RequestParam Integer minPrice,
    @RequestParam Integer maxPrice,@RequestParam Integer minDiscount,@RequestParam String sort,
    @RequestParam String stock,@RequestParam Integer pageNumber,@RequestParam Integer pageSize){

        Page<Product>res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

        return new ResponseEntity<Page<Product>>(res,HttpStatus.FOUND);
    }


    @GetMapping("/products/id/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id")Long productId)throws ProductException{

        Product product = productService.findProductById(productId);

        return new ResponseEntity<Product>(product,HttpStatus.FOUND);
    }
}
