package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
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

    public void handleAddProductToCart(long productId, String username, HttpSession session) {
        User user = this.userService.getUserByEmail(username);
        if (user != null) {
            // check user have cart?
            Cart cart = this.cartRepository.findByUser(user);

            // if user doesn't have cart -> create new cart
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);
                cart = this.cartRepository.save(newCart);
            }

            // save cart detail
            Product product = this.productRepository.findById(productId);
            CartDetail cartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
            /*
             * if cart detail don't exits -> create new cart detail
             * else update quantity of product in cart detail +1
             */
            if (cartDetail == null) {
                CartDetail newCartDetail = new CartDetail();
                newCartDetail.setQuantity(1);
                newCartDetail.setCart(cart);
                if (product != null) {
                    newCartDetail.setProduct(product);
                    newCartDetail.setPrice(product.getPrice());
                }

                // update sum in cart
                int sum = cart.getSum() + 1;
                cart.setSum(sum);
                this.cartDetailRepository.save(newCartDetail);
                this.cartRepository.save(cart);
                session.setAttribute("sumInCart", sum);
            } else {
                cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                this.cartDetailRepository.save(cartDetail);
            }

        }
    }

    public List<CartDetail> getCardDetails(Cart cart) {
        return this.cartDetailRepository.findAllByCart(cart);
    }

    public void handleDeleteProductFromCart(long cartDetailId, HttpSession session) {
        CartDetail cartDetail = this.cartDetailRepository.findById(cartDetailId);
        Cart cart = cartDetail.getCart();
        this.cartDetailRepository.deleteById(cartDetailId);
        if (cart.getSum() > 1) {
            int newSum = cart.getSum() - 1;
            cart.setSum(newSum);
            session.setAttribute("sumInCart", newSum);
            this.cartRepository.save(cart);
        } else if (cart.getSum() == 1) {
            this.cartRepository.delete(cart);
            session.setAttribute("sumInCart", 0);
        }
    }
}
