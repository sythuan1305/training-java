package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartProductOrderRepository extends JpaRepository<CartProductOrderEntity, Integer> {
    List<CartProductOrderEntity> findAllByOrderId(Integer orderId);

    // NOTE - Chưa hiểu vì sao lại phải dùng OrderEntity ở đây
    @Query(value = "SELECT o, cpo, cp, p FROM OrderEntity o " +
            "left join  o.cartProductOrderEntities cpo " +
            "left join  cpo.cartProduct cp " +
            "left join cp.product p " +
            "WHERE cp.cart.id = ?1")
    @NonNull Page<OrderEntity> findAllByCartId(Integer cartId, @NonNull Pageable pageable);
}