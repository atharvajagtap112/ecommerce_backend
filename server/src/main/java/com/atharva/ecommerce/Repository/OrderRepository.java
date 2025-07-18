package com.atharva.ecommerce.Repository;

import com.atharva.ecommerce.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = 'PLACED' OR o.orderStatus = 'ORDER_CONFIRMED' OR o.orderStatus = 'SHIPPED' OR o.orderStatus = 'OUT_FOR_DELIVERY'  OR o.orderStatus = 'DELIVERED')")
    public List<Order> getUserOrders(@Param("userId") Long userId);

}
