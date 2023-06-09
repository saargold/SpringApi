package com.devops.webapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> getStoreOrdersByCustomer(Customer customer);
}
