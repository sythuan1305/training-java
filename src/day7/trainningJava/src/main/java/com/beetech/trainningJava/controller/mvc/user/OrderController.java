package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountModel;
import com.beetech.trainningJava.model.OrderModel;
import com.beetech.trainningJava.payment.paypal.service.PaypalService;
import com.beetech.trainningJava.service.*;
import com.beetech.trainningJava.utils.Utils;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller này dùng để xử lý các request liên quan đến đơn hàng
 */
@Controller("mvcUserOrderController")
@RequestMapping("/user/order")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderController {
    private final ICartProductService cartProductService;

    private final IProductDiscountConditionService productDiscountConditionService;

    private final IAccountService accountService;

    private final IOrderService orderService;

    private final ICartProductOrder cartProductOrderService;

    private final PaypalService paypalService;

    /**
     * Chuyển đến trang xem thông tin đơn hàng
     *
     * @param cartProductId danh sách id sản phẩm trong giỏ hàng
     * @param discountId    id giảm giá
     * @param cookieValue   giá trị của cookie cart
     * @return trang xem thông tin đơn hàng
     */
    @PostMapping("/payment")
    public ModelAndView pay(@RequestParam("cartProductId") Integer[] cartProductId,
                            @RequestParam(value = "discountId", required = false) Integer discountId,
                            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue) {
        ModelAndView modelAndView = new ModelAndView("order/payment");
        // lấy thông tin giỏ hàng từ cookie hoặc database
        List<CartProductInforModel> cartProductInforModels = cartProductService.createCartProductInforListWithLoginOrNotWithCartProductParserList(
                Utils.JsonParserListObjectWithEncodedURL(cookieValue)
        );

        // lấy thông tin sản phẩm trong giỏ hàng theo id mà người dùng chọn
        cartProductInforModels = cartProductService.getCartProductInforModelListByCartProductInforModelListAndCartProductArray(
                cartProductInforModels, cartProductId);

        // lấy thông tin giảm giá
        DiscountModel discountModel = null;
        if (discountId != null) {
            discountModel = productDiscountConditionService.getDiscountModelByDiscountIdAndCartProductInforList(discountId,
                    cartProductInforModels);
        }

        // tạo đối tượng order
        OrderModel orderModel = new OrderModel(cartProductInforModels, discountModel,
                accountService.getCartEntity(), PaymentMethod.PAYPAL, PaymentStatus.PENDING);
        modelAndView.addObject("discountModel", discountModel);
        modelAndView.addObject("cartProducts", cartProductInforModels);
        modelAndView.addObject("orderModel", orderModel);
        return modelAndView;
    }

    /**
     * Xử lý xác thực và chuyển đến linh approve của paypal
     *
     * @param cartProductId danh sách id của sản phẩm trong đơn hàng
     * @param discountId    id của giảm giá
     * @param paymentMethod phương thức thanh toán
     * @param cookieValue   giá trị cookie của giỏ hàng
     * @return trả về đường dẫn approve của paypal
     */
    @PostMapping("/authorizePayment")
    public RedirectView payment(@RequestParam("cartProductId") Integer[] cartProductId,
                                @RequestParam(value = "discountId", required = false) Integer discountId,
                                @RequestParam(value = "paymentMethod") String paymentMethod,
                                @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue) {
        // Lấy thông tin giỏ hàng từ cookie hoặc database
        List<CartProductInforModel> cartProductInforModels = cartProductService.createCartProductInforListWithLoginOrNotWithCartProductParserList(
                Utils.JsonParserListObjectWithEncodedURL(cookieValue)
        );

        // lấy thông tin sản phẩm trong giỏ hàng theo id mà người dùng chọn
        cartProductInforModels = cartProductService.getCartProductInforModelListByCartProductInforModelListAndCartProductArray(
                cartProductInforModels, cartProductId);

        // Chuyển đổi CartProductInforModel sang CartProductEntity để lưu vào CartProductOrderEntity
        List<CartProductEntity> cartProductEntities;
        if (!accountService.isLogin()) {
            // nếu chưa đăng nhập thì lưu vào bảng cart_product
            cartProductEntities = cartProductService
                    .saveCartProductEntityListByCartProductModelList(cartProductInforModels);
        } else {
            // nếu đã đăng nhập thì chuyển đổi sang CartProductEntity
            cartProductEntities = cartProductService
                    .changeCartProductInforModelListToCartProductEntityList(cartProductInforModels);
        }

        // lấy thông tin giảm giá
        DiscountModel discountModel = null;
        if (discountId != null) {
            discountModel = productDiscountConditionService.getDiscountModelByDiscountIdAndCartProductInforList(discountId,
                    cartProductInforModels);
        }

        // tạo đối tượng order và lưu vào bảng order
        OrderModel orderModel = new OrderModel(cartProductInforModels, discountModel, accountService.getCartEntity(),
                PaymentMethod.PAYPAL, PaymentStatus.PENDING);
        OrderEntity orderEntity = orderService.saveOrderEntityByModel(orderModel);

        // lưu vào bảng cart_product_order
        cartProductOrderService.saveCartProductOrderEntityListByCartProductEntityListAndOrderEntity(cartProductEntities, orderEntity);

        // lấy link để chuyển đến trang thanh toán
        String approvalLink = paypalService.authorizePayment(orderModel);

        return new RedirectView(approvalLink);
    }

    @GetMapping("/cancelPayment")
    public ModelAndView cancelPaymentGet() {
        return new ModelAndView("payment/paypal/cancel");
    }

    /**
     * Thực hiện thanh toán với phương thức GET
     *
     * @param paymentId id của thanh toán
     * @param payerId   id của người thanh toán
     * @param response  response
     * @return gọi đến phương thức POST
     */
    @GetMapping("/executePayment")
    public String executePaymentGet(@RequestParam("paymentId") String paymentId,
                                    @RequestParam("PayerID") String payerId,
                                    HttpServletResponse response) {
        return executePaymentPost(paymentId, payerId, response);
    }

    /**
     * Thực hiện thanh toán với phương thức POST
     *
     * @param paymentId id của thanh toán
     * @param payerId   id của người thanh toán
     * @param response  response
     * @return trang thanh toán thành công
     */
    @PostMapping("/executePayment")
    public String executePaymentPost(@RequestParam("paymentId") String paymentId,
                                     @RequestParam("PayerID") String payerId,
                                     HttpServletResponse response) {
        // thực hiện thanh toán
        Payment payment = paypalService.executePayment(paymentId, payerId);

        // nếu chưa đăng nhập thì xóa cookie sau khi thanh toán thành công
        if (!accountService.isLogin()) {
            Utils.deleteCookie("cart", response);
        }

        // lấy thông tin order
        Transaction transaction = payment.getTransactions().get(0);
        OrderEntity orderEntity = orderService.findOrderEntityById(Integer.parseInt(transaction.getInvoiceNumber()));

        // cập nhật trạng thái thanh toán
        cartProductOrderService.updateCartProductOrderEntityListAfterBoughtByOrderEntity(orderEntity);
        return "payment/paypal/success";
    }
}
