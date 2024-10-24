package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import java.util.List;
import java.util.ArrayList;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getItemDetailPage(Model model, @PathVariable long id) {
        model.addAttribute("product", this.productService.getProduct(id));
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        long productId = id;
        HttpSession session = request.getSession(false);
        String username = session.getAttribute("email") + "";
        this.productService.handleAddProductToCart(productId, username, session, 1);
        return "redirect:/";
    }

    @PostMapping("/delete-product-from-cart/{id}")
    public String deleteProduct(@PathVariable long id, HttpServletRequest request) {
        long cartDetailId = id;
        HttpSession session = request.getSession(false);
        this.productService.handleDeleteProductFromCart(cartDetailId, session);
        return "redirect:/cart";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart, Model model) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckOut(cartDetails);
        List<CartDetail> updatedCartDetails = this.productService.getCartDetails(cart);
        double cartTotal = 0;
        for (CartDetail cd : updatedCartDetails) {
            cartTotal += (cd.getPrice() * cd.getQuantity());
        }
        model.addAttribute("cartDetails", updatedCartDetails);
        model.addAttribute(("cartTotal"), cartTotal);
        return "client/cart/checkOut";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long userId = (long) session.getAttribute("id");
        user.setId(userId);
        this.productService.handleCreateOrder(user, session, receiverName, receiverAddress, receiverPhone);
        return "client/cart/thank";
    }

    @PostMapping("/add-product-from-view-detail/{id}")
    public String addProductFromViewDetail(@PathVariable long id, @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = session.getAttribute("email") + "";
        this.productService.handleAddProductToCart(id, email, session, quantity);
        return "redirect:/cart";
    }
}
