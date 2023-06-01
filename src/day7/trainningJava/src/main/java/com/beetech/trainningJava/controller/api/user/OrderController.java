package com.beetech.trainningJava.controller.api.user;

import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.enums.ShippingMethod;
import com.beetech.trainningJava.model.*;
import com.beetech.trainningJava.payment.paypal.service.PaypalService;
import com.beetech.trainningJava.service.*;
import com.beetech.trainningJava.service.imp.EmailService;
import com.beetech.trainningJava.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController("apiUserOrderController")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api/user/order")
public class OrderController {
    private final ICartProductService cartProductService;

    private final IProductDiscountConditionService productDiscountConditionService;

    private final IAccountService accountService;

    private final IOrderService orderService;

    private final EmailService emailService;

    private final ICartProductOrder cartProductOrderService;

    private final PaypalService paypalService;


    @PostMapping("/payment")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Object> pay(
            @RequestParam("cartProductId") Long[] cartProductId,
            @RequestParam(value = "discountId", required = false) Integer discountId,
            @RequestParam(value = "paymentMethod", required = false, defaultValue = "COD") PaymentMethod paymentMethod,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "shippingMethod", required = false, defaultValue = "STANDARD") ShippingMethod shippingMethod,
            @RequestParam(value = "email", required = false) String email,
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue) {
        System.out.println(paymentMethod);
        // Lấy thông tin giỏ hàng từ cookie hoặc database
        List<CartProductEntityDto> cartProductEntityDtos = cartProductService.createCartProductEntityDtoListWithLoginOrNotWithCartProductParserList(
                Utils.JsonParserListObjectWithEncodedURL(cookieValue)
        );

        // lấy thông tin sản phẩm trong giỏ hàng theo id mà người dùng chọn
        cartProductEntityDtos = cartProductService.getCartProductEntityDtoListByCartProductEntityDtoListAndCartProductArray(
                cartProductEntityDtos, cartProductId);

        // Chuyển đổi CartProductEntityDto sang CartProductEntity để lưu vào CartProductOrderEntity
        List<CartProductEntity> cartProductEntities;
        if (!accountService.isLogin()) {
            // nếu chưa đăng nhập thì lưu vào bảng cart_product
            cartProductEntities = cartProductService
                    .saveCartProductEntityListByCartProductEntityDtoList(cartProductEntityDtos);
        } else {
            // nếu đã đăng nhập thì chuyển đổi sang CartProductEntity
            cartProductEntities = cartProductService
                    .changeCartProductEntityDtoListToCartProductEntityList(cartProductEntityDtos);
            email = accountService.getAccountEntity().getEmail();
        }

        // lấy thông tin giảm giá
        DiscountEntityDto discountEntityDto = productDiscountConditionService.getDiscountEntityDtoByDiscountIdAndCartProductEntityDtoList(discountId,
                cartProductEntityDtos);

        // tạo đối tượng order và lưu vào database
        OrderEntity orderEntity = orderService.createAndSaveOrderEntityByCartProductEntityListAndDiscountEntity(
                cartProductEntities,
                discountEntityDto,
                paymentMethod,
                shippingMethod,
                address,
                email);

        // tạo order entity dto để trả về cho người dùng
        OrderEntityDto orderEntityDto = orderService.createOrderEntityDtoByOrderEntity(orderEntity);
        // send mail thông báo đơn hàng
        CompletableFuture<Boolean> isSendMailSuccessfully = emailService.sendPaymentInformationHtmlMessage(orderEntityDto, email);
        Map<String, Object> paymentInformation = new Hashtable<>();
        paymentInformation.put("order", orderEntityDto);
        paymentInformation.put("isSendMailSuccessfully", isSendMailSuccessfully);

        return ResponseEntity.ok(new ApiResponse(
                paymentInformation,
                "Order successfully"));
    }

    // TODO LIST ORDER PAYMENT STATUS
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping("/list")
    public ResponseEntity<Object> listOrder(@PageableDefault Pageable pageable,
                                            @RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus) {
        CartEntity cartEntity = accountService.getCartEntity();
        PageModel<OrderEntityDto> pageModel = orderService.findAllOrderEntityByCartId(pageable, paymentStatus, cartEntity.getId());
        return ResponseEntity.ok(new ApiResponse(pageModel, "get list order success"));
    }
}
