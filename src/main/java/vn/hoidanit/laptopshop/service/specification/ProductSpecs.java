package vn.hoidanit.laptopshop.service.specification;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import java.util.List;

public class ProductSpecs {

    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> minPrice(double minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPrice(double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("price"), maxPrice);
    }

    public static Specification<Product> factoryEquals(String factoryName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("factory"), factoryName);
    }

    public static Specification<Product> factoriesIn(List<String> factories) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("factory")).value(factories);
    }

    public static Specification<Product> priceInRange(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.ge(root.get("price"), minPrice),
                criteriaBuilder.le(root.get("price"), maxPrice));
    }

    public static Specification<Product> priceInRanges(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Product> targetsIn(List<String> targets) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("target")).value(targets);
    }

    public static Specification<Product> dij() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
    }
}
