package com.ecom.ProductCatalog.repository;

import com.ecom.ProductCatalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByCategoryId(Long categoryId);

//    @Query("SELECT p from Product p where "+
//            "LOWER(p.name) like lower(concat( '%', :keyword,'%')) or "+
//            "LOWER(p.description) like lower(concat( '%', :keyword,'%')) or "+
//            "LOWER(p.category.name) like lower(concat( '%', :keyword,'%'))")
//    List<Product> search(@Param("keyword") String keyword);
}
