package com.ecom.ProductCatalog.service;

import com.ecom.ProductCatalog.model.Category;
import com.ecom.ProductCatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;
    public List<Category> showAll() {
        return repository.findAll();
    }
}
