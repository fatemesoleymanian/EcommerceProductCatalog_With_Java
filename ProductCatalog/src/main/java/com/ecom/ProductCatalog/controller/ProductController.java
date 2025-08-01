package com.ecom.ProductCatalog.controller;

import com.ecom.ProductCatalog.model.Product;
import com.ecom.ProductCatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = service.showAllProducts();
        return products.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable long categoryId){
        try {
            List<Product> products = service.showAllProductsByCategory(categoryId);
            return products.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @GetMapping("/search")
//    public ResponseEntity<List<Product>> search(@RequestParam String keyword){
//        try {
//            List<Product> products = service.searchWithKeyWord(keyword);
//            return products.isEmpty()
//                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
//                    : new ResponseEntity<>(products, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/sort")
//    public ResponseEntity<List<Product>> sortByPrice(@RequestParam(defaultValue = "asc") String direction){
//        try {
//            List<Product> products = service.getProductsSortedByPrice(direction);
//            return products.isEmpty()
//                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
//                    : new ResponseEntity<>(products, HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchAndSort(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        try {
            List<Product> products = service.searchAndSort(keyword, sort);
            return products.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
