package com.ecom.ProductCatalog.service;

import com.ecom.ProductCatalog.model.Product;
import com.ecom.ProductCatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> showAllProducts() {
        return repository.findAll();
    }

    public List<Product> showAllProductsByCategory(long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

//    public List<Product> searchWithKeyWord(String keyword) {
//        return repository.search(keyword);
//    }
//    public List<Product> getProductsSortedByPrice(String direction) {
//        Sort sort = direction.equalsIgnoreCase("desc")
//                ? Sort.by("price").descending()
//                : Sort.by("price").ascending();
//        return repository.findAll(sort);
//    }
    public List<Product> searchAndSort(String keyword, String sortDirection) {
        Specification<Product> spec = (root, query, cb) -> {
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern),
                    cb.like(cb.lower(root.get("category").get("name")), likePattern)
            );
        };

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by("price").descending()
                : Sort.by("price").ascending();

        return repository.findAll(spec, sort);
    }
}
