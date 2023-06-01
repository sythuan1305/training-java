package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.enums.ShippingMethod;
import com.beetech.trainningJava.model.DiscountEntityDto;
import com.beetech.trainningJava.model.OrderEntityDto;
import com.beetech.trainningJava.model.OrderModel;
import com.beetech.trainningJava.model.PageModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface của order service
 *
 * @see com.beetech.trainningJava.service.imp.OrderServiceImp
 */
public interface IOrderService {
    /**
     * Lưu order entity vào database
     *
     * @param orderEntity là 1 entity cần lưu vào database
     * @return OrderEntity là thông tin của order sau khi lưu vào database
     */
    OrderEntity saveOrderEntityByEntity(OrderEntity orderEntity);

    /**
     * Tìm order entity theo id
     *
     * @param id là id của order cần tìm
     * @return OrderEntity là thông tin của order sau khi tìm thấy
     */
    OrderEntity findOrderEntityById(Integer id);

    /**
     * Lưu order model vào database
     *
     * @param orderModel là 1 model cần lưu vào database
     * @return OrderEntity là thông tin của order sau khi lưu vào database
     */
    OrderEntity saveOrderEntityByModel(OrderModel orderModel);

    /**
     * Tạo và lưu order entity vào database
     *
     * @param cartProductEntityList là danh sách cart product entity
     * @param discountEntityDto     là discount entity dto
     * @param paymentMethod         là payment method
     * @param email
     * @return OrderEntity là thông tin của order sau khi lưu vào database
     */
    OrderEntity createAndSaveOrderEntityByCartProductEntityListAndDiscountEntity(
            List<CartProductEntity> cartProductEntityList,
            DiscountEntityDto discountEntityDto,
            PaymentMethod paymentMethod,
            ShippingMethod shippingMethod,
            String shippingAddress,
            String email);

    /**
     * Tạo order entity dto từ order entity
     *
     * @param orderEntity là order entity dùng để tạo order entity dto
     * @return OrderEntityDto là order entity dto sau khi tạo
     */
    OrderEntityDto createOrderEntityDtoByOrderEntity(OrderEntity orderEntity);

    /**
     * Cập nhật trạng thái thanh toán của order
     *
     * @param orderId       là id của order cần cập nhật
     * @param paymentStatus là trạng thái thanh toán mới
     * @return OrderEntity là thông tin của order sau khi cập nhật
     */
    OrderEntity updatePaymentStatus(Integer orderId, PaymentStatus paymentStatus);

    /**
     * Tìm tất cả order entity theo trạng thái thanh toán
     * @param pageable phân trang
     * @param paymentStatus trạng thái thanh toán
     * @return là danh sách order entity theo trạng thái thanh toán và phân trang
     */
    PageModel<OrderEntityDto> findAllOrderEntity(Pageable pageable, PaymentStatus paymentStatus);

    /**
     * Tìm tất cả order entity theo trạng thái thanh toán và cart id và phân trang
     * @param pageable phân trang
     * @param paymentStatus trạng thái thanh toán
     * @param cartId id của cart
     * @return là danh sách order entity theo trạng thái thanh toán và cart id và phân trang
     */
    PageModel<OrderEntityDto> findAllOrderEntityByCartId(Pageable pageable, PaymentStatus paymentStatus, Integer cartId);
}
