package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.boot.autoconfigure.rsocket.RSocketProperties.Server.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.specification.ProductSpecs;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public void handleSaveProduct(Product product) {
        this.productRepository.save(product);
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public Page<Product> getProductsWithSpec(Pageable pageable, ProductCriteriaDTO productCriteriaDTO) {
        Specification<Product> combinedSpec = Specification.where(null);
        if (productCriteriaDTO.getFactory() == null && productCriteriaDTO.getTarget() == null
                && productCriteriaDTO.getPrice() == null) {
            return this.productRepository.findAll(pageable);
        }
        if (productCriteriaDTO.getFactory() != null ? productCriteriaDTO.getFactory().isPresent() : false) {
            combinedSpec = combinedSpec.and(ProductSpecs.factoriesIn(productCriteriaDTO.getFactory().get()));
        }
        if (productCriteriaDTO.getTarget() != null ? productCriteriaDTO.getTarget().isPresent() : false) {
            combinedSpec = combinedSpec.and(ProductSpecs.targetsIn(productCriteriaDTO.getTarget().get()));
        }
        if (productCriteriaDTO.getPrice() != null ? productCriteriaDTO.getPrice().isPresent() : false) {
            Specification<Product> currentSpec = this.buildPriceSpecification(productCriteriaDTO.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpec);
        }
        return this.productRepository.findAll(combinedSpec, pageable);
    }

    public Specification<Product> buildPriceSpecification(List<String> prices) {
        Specification<Product> combinedSpec = Specification.where(null);
        for (String price : prices) {
            double min = 0;
            double max = 0;
            switch (price) {
                case "duoi-10-trieu": {
                    min = 0;
                    max = 10000000;
                    break;
                }
                case "10-15-trieu": {
                    min = 10000000;
                    max = 15000000;
                    break;
                }
                case "15-20-trieu": {
                    min = 15000000;
                    max = 20000000;
                    break;
                }
                case "tren-20-trieu": {
                    min = 20000000;
                    max = 900000000;
                    break;
                }
            }
            if (min == 0 && max == 10000000) {
                Specification<Product> priceSpec = ProductSpecs.maxPrice(max);
                combinedSpec = combinedSpec.or(priceSpec);
            }
            if (min != 0 && max != 0) {
                Specification<Product> priceSpec = ProductSpecs.priceInRanges(min, max);
                combinedSpec = combinedSpec.or(priceSpec);
            }
        }
        return combinedSpec;
    }

    // public Page<Product> getProductsWithSpec(Pageable pageable, double maxPrice)
    // {
    // return this.productRepository.findAll(ProductSpecs.maxPrice(maxPrice),
    // pageable);
    // }

    // public Page<Product> getProductsWithSpec(Pageable pageable, double minPrice)
    // {
    // return this.productRepository.findAll(ProductSpecs.minPrice(minPrice),
    // pageable);
    // }

    // public Page<Product> getProductsWithSpec(Pageable pageable, String
    // nameFactory) {
    // return
    // this.productRepository.findAll(ProductSpecs.factoryEquals(nameFactory),
    // pageable);
    // }

    // public Page<Product> getProductsWithSpec(Pageable pageable, List<String>
    // factories) {
    // return this.productRepository.findAll(ProductSpecs.factoriesIn(factories),
    // pageable);
    // }

    // public Page<Product> getProductsWithSpec(Pageable pageable, String price) {
    // if (price.equals("10-toi-15-trieu")) {
    // double minPrice = 10000000;
    // double maxPrice = 15000000;
    // return this.productRepository.findAll(ProductSpecs.priceInRange(minPrice,
    // maxPrice), pageable);
    // } else if (price.equals("15-toi-20-trieu")) {
    // double minPrice = 15000000;
    // double maxPrice = 20000000;
    // return this.productRepository.findAll(ProductSpecs.priceInRange(minPrice,
    // maxPrice), pageable);
    // }
    // return this.productRepository.findAll(pageable);

    // }

    // public Page<Product> getProductsWithSpec(Pageable pageable, String price) {
    // return this.productRepository.findAll(pageable);
    // }

    // public Page<Product> getProductsWithSpec(Pageable pageable, List<String>
    // prices) {
    // Specification<Product> combinedSpec = ProductSpecs.dij();
    // int count = 0;
    // for (String price : prices) {
    // double min = 0;
    // double max = 0;

    // switch (price) {
    // case "10-toi-15-trieu": {
    // min = 10000000;
    // max = 15000000;
    // count++;
    // break;
    // }
    // case "15-toi-20-trieu": {
    // min = 15000000;
    // max = 20000000;
    // count++;
    // break;
    // }
    // case "20-toi-30-trieu": {
    // min = 20000000;
    // max = 30000000;
    // count++;
    // break;
    // }
    // }

    // if (min != 0 && max != 0) {
    // Specification<Product> rangeSpec = ProductSpecs.priceInRanges(min, max);
    // combinedSpec = combinedSpec.or(rangeSpec);
    // }
    // }

    // if (count != 0) {
    // return this.productRepository.findAll(combinedSpec, pageable);
    // }

    // return this.productRepository.findAll(pageable);
    // }

    public Page<Product> getProducts(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public Product getProduct(long id) {
        return this.productRepository.findById(id);
    }

    public void handleDeleteProduct(Product product) {
        this.productRepository.delete(product);
    }

    public void handleAddProductToCart(long productId, String username, HttpSession session, long quantity) {
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
                newCartDetail.setQuantity(quantity);
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
                cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
                this.cartDetailRepository.save(cartDetail);
            }

        }
    }

    public List<CartDetail> getCartDetails(Cart cart) {
        return this.cartDetailRepository.findAllByCart(cart);
    }

    public Cart getCartByUser(User user) {
        return this.cartRepository.findByUser(user);
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

    public void handleUpdateCartBeforeCheckOut(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            CartDetail currentCartDetail = this.cartDetailRepository.findById(cartDetail.getId());
            if (currentCartDetail != null) {
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handleCreateOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        // get Cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            double sum = 0;
            for (CartDetail cartDetail : cartDetails) {
                sum += cartDetail.getQuantity() * cartDetail.getPrice();
            }
            // create new empty order
            Order order = new Order();
            order.setUser(user);
            order.setReceiverName(receiverName);
            order.setReceiverAddress(receiverAddress);
            order.setReceiverPhone(receiverPhone);
            order.setStatus("PENDING");
            order.setTotalPrice(sum);
            order = this.orderRepository.save(order);
            for (CartDetail cartDetail : cartDetails) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(cartDetail.getProduct());
                orderDetail.setPrice(cartDetail.getPrice());
                orderDetail.setQuantity(cartDetail.getQuantity());
                orderDetail.setOrder(order);
                this.orderDetailRepository.save(orderDetail);
            }

            for (CartDetail cartDetail : cartDetails) {
                this.cartDetailRepository.deleteById(cartDetail.getId());
            }
            this.cartRepository.deleteById(cart.getId());

            session.setAttribute("sumInCart", 0);
        }

    }
}
