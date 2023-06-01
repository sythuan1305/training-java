package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentStatus;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @EntityGraph(attributePaths = {"cartProductOrderEntities", "cartProductOrderEntities.cartProduct"})
    Optional<OrderEntity> findOrderEntityById(Integer id);

    @NonNull Page<OrderEntity> findAll(@NonNull Pageable pageable);

    @NonNull Page<OrderEntity> findAllByPaymentStatus(PaymentStatus paymentStatus, @NonNull Pageable pageable);

    @NonNull Page<OrderEntity> findAllByPaymentStatusAndCartId(PaymentStatus paymentStatus, Integer cartId, @NonNull Pageable pageable);

    @NonNull Page<OrderEntity> findAllByCartId(Integer cartId, @NonNull Pageable pageable);

    @Query(value = "SELECT cpo, cp, p, o FROM CartProductOrderEntity cpo " +
            "left join  cpo.order o " +
            "left join  cpo.cartProduct cp " +
            "left join cp.product p " +
            "WHERE cp.cart.id = ?1")
    @NonNull Page<CartProductOrderEntity> findAllCartProductOrderByCartId(Integer cartId, @NonNull Pageable pageable);

}