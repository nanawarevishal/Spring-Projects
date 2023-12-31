package com.zosh.ecommersyoutube.Service;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Model.Size;
import com.zosh.ecommersyoutube.Request.CreateProductRequest;

@Service
public interface ProductService {
    
    public Product createProduct(CreateProductRequest request);

    public String deleteProduct(Long productId)throws ProductException;

    public Product updateProduct(Long productId,Product request)throws ProductException;

    public Product findProductById(Long productId)throws ProductException;

    public List<Product> findProductByCategory(String category)throws ProductException;
    
    public Page<Product> getAllProduct(String category,List<String>colors,List<String>sizes,Integer minPrice,Integer maxPrice, Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);

    public List<Product>findAllProducts();

}
