package com.atharva.ecommerce.Repository;

import com.atharva.ecommerce.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c From Cart c Where c.user.id=:userId")
    public Cart findByUserId(@Param("userId") Long userId);
}
