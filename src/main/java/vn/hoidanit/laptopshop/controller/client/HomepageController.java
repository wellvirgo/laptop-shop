package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
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
        List<Product> products = this.productService.getProducts();
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
}
