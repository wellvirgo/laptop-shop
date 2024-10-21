package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import java.util.List;

@Controller
public class CartController {

    private final ProductService productService;
    private final UserService userService;

    public CartController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String getCartDetailPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        User user = this.userService.getUserById((long) session.getAttribute("id"));
        List<CartDetail> cartDetails = this.productService.getCardDetails(user.getCart());
        double cartTotal = 0;
        for (CartDetail cd : cartDetails) {
            cartTotal += (cd.getPrice() * cd.getQuantity());
        }
        model.addAttribute("cardDetailList", cartDetails);
        model.addAttribute("cartTotal", cartTotal);
        return "client/cart/show";
    }
}
