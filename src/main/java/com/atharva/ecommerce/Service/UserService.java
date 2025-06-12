package com.atharva.ecommerce.Service;

import com.atharva.ecommerce.Exception.UserException;
import com.atharva.ecommerce.Model.User;
import com.atharva.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UserService {



     public User findUserById(Long userId) throws UserException;

     public User findUserByJwtToken(String jwt) throws UserException;



}
