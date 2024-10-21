package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    public CartDetail findByCartAndProduct(Cart cart, Product product);

    public List<CartDetail> findAllByCart(Cart cart);

    public CartDetail findById(long id);

    public void deleteById(long id);
}