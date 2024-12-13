package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class HomepageController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public HomepageController(ProductService productService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.getTopProducts(4);
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/access-denied")
    public String getDenyPage() {
        return "client/auth/deny";
    }

    @GetMapping("/history-order")
    public String getHistoryOrderPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long userId = (long) session.getAttribute("id");
        User user = this.userService.getUserById(userId);
        List<Order> orders = this.orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "client/order/history";
    }

    @GetMapping("/products")
    public String getProductsPage(Model model, ProductCriteriaDTO productCriteriaDTO,
            HttpServletRequest request) {
        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(page - 1, 6);
        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            if (productCriteriaDTO.getSort().get().equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 6, Sort.by(Product_.PRICE).descending());
            } else if (productCriteriaDTO.getSort().get().equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 6, Sort.by(Product_.PRICE).ascending());
            }
        }

        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isBlank()) {
            queryString = queryString.replace("page=" + page, "");
        }
        Page<Product> products = this.productService.getProductsWithSpec(pageable, productCriteriaDTO);
        List<Product> productsList = products.getContent();
        model.addAttribute("products", productsList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("queryString", queryString);
        return "client/product/show";
    }
}
