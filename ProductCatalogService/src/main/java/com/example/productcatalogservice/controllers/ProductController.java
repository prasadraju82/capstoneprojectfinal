package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.State;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

//For Displaying use of Qualifier
//    @Autowired
//    @Qualifier("fkps")
//    private IProductService productService1;

    @Autowired
    //@Qualifier("sps")
    private IProductService productService2;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productService2.getAllProducts();
        for(Product product  : products) {
            productDtos.add(from(product));
        }

        return productDtos;
    }

    @GetMapping("{pid}/{uid}")
    public ProductDto getProductDetailsBasedOnUserRole(@PathVariable Long pid,@PathVariable Long uid) {
        Product product = productService2.getProductBasedOnUser(pid,uid);
        if(product == null) return  null;
        return from(product);
    }


    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long productId) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

            if (productId < 0) {
                headers.add("called by", "bhudwak");
                //return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
                throw new IllegalArgumentException("Please try with productId > 0");
            } else if(productId == 0) {
                throw new IllegalArgumentException("No No !!");
            }

            Product product = productService2.getProductById(productId);
            headers.add("called by", "intelligent");
            if (product == null) return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(from(product), headers, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw exception;
        }
    }

    private ProductDto from (Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    //HW -: TO BE DONE BY LEARNERS
    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product input = from(productDto);
        Product output = productService2.save(input);
        return from(output);

    }

    @PutMapping("/{id}")
    public ProductDto replaceProduct(@PathVariable Long id,@RequestBody ProductDto request) {
        Product productRequest = from(request);
        Product product  = productService2.replaceProduct(id,productRequest);
        return  from(product);
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
//        product.setCreatedAt(new Date());
//        product.setLastUpdatedAt(new Date());
//        product.setState(State.ACTIVE);
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }

}
