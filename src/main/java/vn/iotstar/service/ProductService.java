package vn.iotstar.service;

import vn.iotstar.entity.*;
import java.util.List;
public interface ProductService {
    List<Product> findAll();
    List<Product> getAllOrderByPriceAsc();
    List<Product> getByCategory(Long categoryId);
    Product findById(Long id);
    Product save(Product p);
    Product update(Long id, Product p);
    void delete(Long id);
}