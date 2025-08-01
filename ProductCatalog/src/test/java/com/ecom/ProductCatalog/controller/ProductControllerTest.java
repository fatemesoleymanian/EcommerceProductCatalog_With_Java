package com.ecom.ProductCatalog.controller;

import com.ecom.ProductCatalog.model.Category;
import com.ecom.ProductCatalog.model.Product;
import com.ecom.ProductCatalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void testGetProductsReturnsList() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setName("Phone");
        p.setPrice(500.0);
        p.setCategory(new Category());

        when(service.showAllProducts()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Phone"));
    }

    @Test
    void testGetProductsNoContent() throws Exception {
        when(service.showAllProducts()).thenReturn(List.of());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        Product p = new Product();
        p.setId(2L);
        p.setName("Laptop");
        p.setCategory(new Category());

        when(service.showAllProductsByCategory(1L)).thenReturn(List.of(p));

        mockMvc.perform(get("/api/products/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }
}
