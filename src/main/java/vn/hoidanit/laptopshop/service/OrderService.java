package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order getOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return this.orderRepository.save(order);
    }

    public void handleDeleteOrder(Order deleteOrder, List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            this.orderDetailRepository.delete(orderDetail);
        }
        this.orderRepository.delete(deleteOrder);
    }

    public List<Order> getOrdersByUser(User user) {
        return this.orderRepository.findAllByUser(user);
    }
}
