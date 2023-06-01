package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.enums.ShippingMethod;
import com.beetech.trainningJava.model.DiscountEntityDto;
import com.beetech.trainningJava.model.OrderEntityDto;
import com.beetech.trainningJava.model.OrderModel;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.mappper.OrderEntityDtoMapper;
import com.beetech.trainningJava.repository.OrderRepository;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Class này dùng để triển khai các phương thức của interface IOrderService
 *
 * @see IOrderService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderServiceImp implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderEntityDtoMapper orderEntityDtoMapper;
    private final IAccountService accountService;
    private final EmailService emailService;

    @Override
    public OrderEntity saveOrderEntityByEntity(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity findOrderEntityById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public OrderEntity saveOrderEntityByModel(OrderModel orderModel) {
        OrderEntity orderEntity = new OrderEntity(orderModel);
        orderEntity = saveOrderEntityByEntity(orderEntity);
        orderModel.setId(orderEntity.getId());
        return orderEntity;
    }

    @Override
    public OrderEntity createAndSaveOrderEntityByCartProductEntityListAndDiscountEntity(
            List<CartProductEntity> cartProductEntityList,
            DiscountEntityDto discountEntityDto,
            PaymentMethod paymentMethod,
            ShippingMethod shippingMethod,
            String address,
            String email) {
        OrderEntity orderEntity = new OrderEntity(cartProductEntityList, discountEntityDto, paymentMethod, shippingMethod, address, email);
        orderEntity = saveOrderEntityByEntity(orderEntity);
        return orderEntity;
    }

    @Override
    public OrderEntityDto createOrderEntityDtoByOrderEntity(OrderEntity orderEntity) {
        return orderEntityDtoMapper.apply(orderEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity updatePaymentStatus(Integer orderId, PaymentStatus paymentStatus) {
        Optional<OrderEntity> orderEntity = orderRepository.findOrderEntityById(orderId);
        if (orderEntity.isEmpty())
            throw new RuntimeException("Order not found");
        orderEntity.get().setPaymentStatus(paymentStatus);
        switch (paymentStatus) {
            case PAID -> {
                orderEntity.get().getCartProductOrderEntities().forEach(cartProductOrderEntity -> {
                    cartProductOrderEntity.getCartProduct().setBought(true);
                });
                orderEntity.get().setOrderDate(orderEntity.get().getOrderDate().plusDays(3));

                emailService.sendPaymentSuccessHtmlMessage(orderEntityDtoMapper.apply(orderEntity.get()), orderEntity.get().getEmail());
            }
            case CANCELLED -> {
                emailService.sendPaymentCancelHtmlMessage(orderEntityDtoMapper.apply(orderEntity.get()), orderEntity.get().getEmail());
            }
        }

        return orderEntity.get();
    }

    @Override
    public PageModel<OrderEntityDto> findAllOrderEntity(Pageable pageable, PaymentStatus paymentStatus) {

        Page<OrderEntity> orderEntityPage = switch (paymentStatus) {
            case PAID, CANCELLED, PENDING -> orderRepository.findAllByPaymentStatus(paymentStatus, pageable);
            case null -> orderRepository.findAll(pageable);
        };
        return new PageModel<>(
                orderEntityPage.getContent().stream().map(orderEntityDtoMapper::applyWithNoCartProduct).toList(),
                pageable.getPageNumber(),
                orderEntityPage.getTotalPages());
    }

    @Override
    public PageModel<OrderEntityDto> findAllOrderEntityByCartId(Pageable pageable, PaymentStatus paymentStatus, Integer cartId) {
        Page<OrderEntity> orderEntityPage = switch (paymentStatus) {
            case PAID, CANCELLED, PENDING -> orderRepository.findAllByPaymentStatusAndCartId(paymentStatus, cartId, pageable);
            case null -> orderRepository.findAllByCartId(cartId, pageable);
        };
        return new PageModel<>(
                orderEntityPage.getContent().stream().map(orderEntityDtoMapper).toList(),
                pageable.getPageNumber(),
                orderEntityPage.getTotalPages());
    }

}
