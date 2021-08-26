package com.canada.aws.repo;

import com.canada.aws.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query("SELECT c FROM CartItem c WHERE c.customer.userId =:customer_id")
    List<CartItem> findAllByCustomerId(Integer customer_id);

    @Query("SELECT c FROM CartItem c WHERE c.customer.userId =:customer_id AND c.product.id =:product_id")
    List<CartItem> findAllByCustomerIdAndProductId(Integer customer_id, Integer product_id);

    @Transactional
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity =:quantity WHERE c.customer.userId =:customerId AND c.product.id =:productId")
    void updateQuantity(Integer quantity, Integer customerId, Integer productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.customer.userId =:customerId AND c.product.id =:productId")
    void deleteByCustomerIdAndProductId(Integer customerId, Integer productId);
}
