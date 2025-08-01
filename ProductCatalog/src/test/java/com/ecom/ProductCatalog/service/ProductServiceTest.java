package com.ecom.ProductCatalog.service;

import com.ecom.ProductCatalog.model.Category;
import com.ecom.ProductCatalog.model.Product;
import com.ecom.ProductCatalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product1;
    private Product product2;
    private Category category;

    @BeforeEach
    void setup() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Phone");
        product1.setCategory(category);
        product1.setPrice(500);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Laptop");
        product2.setCategory(category);
        product2.setPrice(1000);
    }

    @Test
    void testShowAllProducts() {
        when(repository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = service.showAllProducts();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testShowProductsByCategory() {
        when(repository.findByCategoryId(1L)).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = service.showAllProductsByCategory(1L);

        assertEquals(2, result.size());
        verify(repository).findByCategoryId(1L);
    }

    @Test
    void testSearchAndSortAsc() {
        Sort expectedSort = Sort.by("price").ascending();

        when(repository.findAll(any(Specification.class), eq(expectedSort)))
                .thenReturn(List.of(product1));

        List<Product> result = service.searchAndSort("phone", "asc");

        assertEquals(1, result.size());
        assertEquals("Phone", result.get(0).getName());

        verify(repository).findAll(any(Specification.class), eq(expectedSort));
    }

    @Test
    void testSearchAndSortDesc() {
        Sort expectedSort = Sort.by("price").descending();

        when(repository.findAll(any(Specification.class), eq(expectedSort)))
                .thenReturn(List.of(product2, product1));

        List<Product> result = service.searchAndSort("laptop", "desc");

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());

        verify(repository).findAll(any(Specification.class), eq(expectedSort));
    }
}
