package com.zosh.ecommersyoutube.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Category;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Model.Size;
import com.zosh.ecommersyoutube.Repository.CategoryRepository;
import com.zosh.ecommersyoutube.Repository.ProductRepository;
import com.zosh.ecommersyoutube.Request.CreateProductRequest;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired 
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    

    @Override
    public Product createProduct(CreateProductRequest request) {
        
        Category topLevel = categoryRepository.findByName(request.getTopLevelCategory());

        if(topLevel == null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(request.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(request.getSecondLevelCategory(), topLevel.getName());

        if(secondLevel == null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(request.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }


        Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLevelCategory(), secondLevel.getName());

        if(thirdLevel == null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(request.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setColor(request.getColor());
        product.setDescription(request.getDescription());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setSizes(request.getSize());
        product.setQuantity(request.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        
        Product product = productRepository.findById(productId).get();

        if(product!=null){

            product.getSizes().clear();
    
            productRepository.delete(product);
    
            return "Product Deleted successfully...!";
        }

        else{
            throw new ProductException("Product not found with id: "+productId);
        }

    }

    @Override
    public Product updateProduct(Long productId,Product request) throws ProductException {

        Product product = productRepository.findById(productId).get();

        if(product!=null){

            if(request.getQuantity()!=0){
                product.setQuantity(request.getQuantity());
            }
            
            return productRepository.save(product);
        }

        else{
           throw new ProductException("Product not found with id: "+productId);
        }
        
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {

        Product product = productRepository.findById(productId).get();

        if(product!=null){

            return product;
        }

        else{
            throw new ProductException("Product not found with id: "+productId);
        }
    }

    @Override
    public List<Product> findProductByCategory(String category) throws ProductException {
        
        List<Product> prouducts = findProductByCategory(category);
        return prouducts;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
            
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product>products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if(!colors.isEmpty()){
            products = products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        if(stock!=null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }

            else if(stock.equals("out_of_stock")){
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());

        List<Product>pageContent = products.subList(startIndex, endIndex);

        Page<Product>filteredProducts = new PageImpl<>(pageContent,pageable,products.size());

        return filteredProducts;

    }

    @Override
    public List<Product> findAllProducts() {
       List<Product>products = productRepository.findAll();
        return products;
    }
    
}
