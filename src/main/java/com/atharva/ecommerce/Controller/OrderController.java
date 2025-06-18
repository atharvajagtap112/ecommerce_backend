package com.atharva.ecommerce.Controller;

import com.atharva.ecommerce.Exception.OrderException;
import com.atharva.ecommerce.Exception.UserException;
import com.atharva.ecommerce.Model.Address;
import com.atharva.ecommerce.Model.Order;
import com.atharva.ecommerce.Model.User;
import com.atharva.ecommerce.Service.OrderService;
import com.atharva.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shppingAdress,
                                             @RequestHeader("Authorization") String jwt) throws UserException{

        User user=userService.findUserProfileByJwt(jwt);

       Order order= orderService.createOrder(user,shppingAdress);
        System.out.println("Order "+order);
       return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrdersHistory(@RequestHeader("Authorization") String jwt) throws UserException{

        User user =userService.findUserProfileByJwt(jwt);
       List<Order> orders= orderService.usersOrderHistory(user.getId());
       return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("Id") Long id,
                                              @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user =userService.findUserProfileByJwt(jwt);
        Order order=orderService.findOrderById(id);
        return new  ResponseEntity<Order>(order,HttpStatus.CREATED);
    }

}
