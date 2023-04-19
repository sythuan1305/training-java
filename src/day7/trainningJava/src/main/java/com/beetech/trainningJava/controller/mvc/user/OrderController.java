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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller("mvcUserOrderController")
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private ICartProductService cartProductService;

    @Autowired
    private IProductDiscountConditionService productDiscountConditionService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartProductOrder  cartProductOrderService;

    @Autowired
    private PaypalService paypalService;

    Payment payment = new Payment();


//    @Autowired
//    private IOrderService orderService;

    @PostMapping("/payment")
    public ModelAndView pay(@RequestParam("cartProductId") Integer[] cartProductId,
                            @RequestParam(value = "discountId", required = false) Integer discountId,
                            @CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue) {
        System.out.println("cartProductId.length" + cartProductId.length);
        System.out.println(("cart" + cookieValue));
        ModelAndView modelAndView = new ModelAndView("order/payment");
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        DiscountModel discountModel = null;
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService.getListCartProductInforByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
        } else {
            cartProductInforModels = cartProductService.getListCartProductInforWithParser(
                    Utils.JsonParserListObjectWithEncodedURL(cookieValue));
        }
//        Arrays.stream(cartProductId).forEach(cartProductIdItem -> {
//                    System.out.println("cartProductIdItem" + cartProductIdItem);
//        });

//        cartProductInforModels.forEach(cartProductInforModel -> {
//            System.out.println("cartProductInforModel.getId()" + cartProductInforModel.getId());
//        });

        cartProductInforModels = cartProductService.getListCartProductInforWithCartProductArray(cartProductInforModels, cartProductId);
//        cartProductInforModels.forEach(cartProductInforModel -> {
//            System.out.println("cartProductInforModel.getProduct().getName()" + cartProductInforModel.getProduct().getName());
//        });
//        System.out.println("cartProductInforModels.size()" + cartProductInforModels.size());
        if (discountId != null) {
            discountModel = productDiscountConditionService.getDiscountModel(discountId, cartProductInforModels);
        }
        OrderModel orderModel = new OrderModel(cartProductInforModels, discountModel,
                accountService.getCartEntity(), PaymentMethod.PAYPAL, PaymentStatus.PENDING);
        modelAndView.addObject("discountModel", discountModel);
        modelAndView.addObject("cartProducts", cartProductInforModels);
        modelAndView.addObject("orderModel", orderModel);
        return modelAndView;
    }

    @PostMapping("/authorizePayment")
    public RedirectView payment(@RequestParam("cartProductId") Integer[] cartProductId,
                                @RequestParam(value = "discountId", required = false) Integer discountId,
                                @RequestParam(value = "paymentMethod") String paymentMethod,
                                @CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue) {
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        DiscountModel discountModel = null;
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService.getListCartProductInforByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
        } else {
            cartProductInforModels = cartProductService.getListCartProductInforWithParser(
                    Utils.JsonParserListObjectWithEncodedURL(cookieValue));
        }
        cartProductInforModels = cartProductService.getListCartProductInforWithCartProductArray(cartProductInforModels, cartProductId);
        //
        List<CartProductEntity> cartProductEntities = new ArrayList<>();
        if (!accountService.isLogin())
        {
            cartProductEntities = cartProductService.saveCartProductInforModelList(cartProductInforModels);
        }
        else {
            cartProductEntities = cartProductService.changeCartProductInforToCartProductEntity(cartProductInforModels);
        }
        //
        if (discountId != null) {
            discountModel = productDiscountConditionService.getDiscountModel(discountId, cartProductInforModels);
        }
        OrderModel orderModel = new OrderModel(cartProductInforModels, discountModel, accountService.getCartEntity(), PaymentMethod.PAYPAL, PaymentStatus.PENDING);
        OrderEntity orderEntity = orderService.save(orderModel);

        List<CartProductOrderEntity> cartProductOrderEntities = cartProductOrderService.saveAll(cartProductEntities, orderEntity);

//        System.out.println("orderModel.getTotalAmount()" + orderModel.getTotalAmount());
//        System.out.println("orderModel.getTotalDiscount()" + orderModel.getTotalDiscount());
//        System.out.println("orderModel.getTotalPrice()" + orderModel.getTotalPrice());
        String approvalLink = paypalService.authorizePayment(orderModel);

        return new RedirectView(approvalLink);
    }

    @GetMapping("/cancelPayment")
    public ModelAndView cancelPaymentGet(
//            @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId
                                         ) {
//        System.out.println("paymentId" + paymentId);
//        System.out.println("payerId" + payerId);

        return new ModelAndView("payment/paypal/cancel");
    }
    @GetMapping("/executePayment")
    public String executePaymentGet(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                                          @CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue,
                                          HttpServletResponse response) {
        return executePaymentPost(paymentId, payerId, cookieValue, response);
    }

    @PostMapping("/executePayment")
    public String executePaymentPost(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                                           @CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue,
                                           HttpServletResponse response) {
        if (!accountService.isLogin())
        {
            Utils.deleteCookie("cart", response);
        }
        Payment payment = paypalService.executePayment(paymentId, payerId);
        System.out.println("payment.getState()" + payment.getState());
        if (payment.getState().equals("approved")) {
            System.out.println("payment approved");
        }
        else {
            System.out.println("payment not approved");
        }
        Transaction transaction = payment.getTransactions().get(0);

        System.out.println("transaction.getInvoiceNumber()" + transaction.getInvoiceNumber());
        OrderEntity orderEntity = orderService.findById(Integer.parseInt(transaction.getInvoiceNumber()));

        List<CartProductOrderEntity> cartProductOrderEntities = cartProductOrderService.updateCartProductOrderAfterBought(orderEntity);
        return "payment/paypal/success";
    }

}
