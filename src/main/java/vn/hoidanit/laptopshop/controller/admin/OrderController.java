package vn.hoidanit.laptopshop.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrderPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Order> orders = this.orderService.getAllOrders(pageable);
        List<Order> orderList = orders.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("orders", orderList);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(@PathVariable long id, Model model) {
        long orderId = id;
        Order currentOrder = this.orderService.getOrderById(orderId);
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (currentOrder != null) {
            orderDetails = currentOrder.getOrderDetails();
        }
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("id", orderId);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(@PathVariable long id, Model model) {
        long orderId = id;
        Order currentOrder = this.orderService.getOrderById(orderId);
        model.addAttribute("currentOrder", currentOrder);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String updateOrder(@ModelAttribute("currentOrder") Order currentOrder) {
        long updatedOrderId = currentOrder.getId();
        Order updatedOrder = this.orderService.getOrderById(updatedOrderId);
        updatedOrder.setStatus(currentOrder.getStatus());
        this.orderService.saveOrder(updatedOrder);
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeletePage(@PathVariable long id, Model model) {
        long orderId = id;
        Order currentOrder = this.orderService.getOrderById(orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("currentOrder", currentOrder);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String deleteOrder(@ModelAttribute("currentOrder") Order currentOrder) {
        long deleteOrderId = currentOrder.getId();
        Order deleteOrder = this.orderService.getOrderById(deleteOrderId);
        List<OrderDetail> orderDetails = deleteOrder.getOrderDetails();
        this.orderService.handleDeleteOrder(deleteOrder, orderDetails);
        return "redirect:/admin/order";
    }
}
