package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    public Product findById(long id);

    public List<Product> findByOrderBySoldDesc(Limit limit);

    @NonNull
    public Page<Product> findAll(@NonNull Pageable page);

    @NonNull
    public Page<Product> findAll(@NonNull Specification<Product> specification, @NonNull Pageable pageable);
}
