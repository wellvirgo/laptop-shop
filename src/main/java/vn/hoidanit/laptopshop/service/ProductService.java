package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void handleSaveProduct(Product product) {
        this.productRepository.save(product);
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public Product getProduct(long id) {
        return this.productRepository.findById(id);
    }

    public void handleDeleteProduct(Product product) {
        this.productRepository.delete(product);
    }
}
