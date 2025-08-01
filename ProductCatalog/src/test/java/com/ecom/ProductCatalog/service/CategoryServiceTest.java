package com.ecom.ProductCatalog.service;

import com.ecom.ProductCatalog.model.Category;
import com.ecom.ProductCatalog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
    }

    @Test
    void testShowAllReturnsList() {
        when(repository.findAll()).thenReturn(List.of(category));

        List<Category> result = service.showAll();

        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testShowAllReturnsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<Category> result = service.showAll();

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }
}
