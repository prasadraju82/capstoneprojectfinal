package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @Autowired
    private IProductService productService;

    @Test
    public void Test_GetProductById_ReturnsProductSuccessfully() {
        //Arrange
        Long productId = 4L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");
        when(productService.getProductById(productId)).thenReturn(product);


        //Act
        ResponseEntity<ProductDto> response =  productController.findProductById(productId);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(productId,response.getBody().getId());
        assertEquals("Iphone",response.getBody().getName());
        verify(productService,times(1)).getProductById(productId);
    }
}