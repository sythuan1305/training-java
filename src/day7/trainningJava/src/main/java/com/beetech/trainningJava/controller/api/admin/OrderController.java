package com.beetech.trainningJava.controller.api.admin;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.OrderEntityDto;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiAdminOrderController")
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/payment-status")
    public ResponseEntity<Object> updatePaymentStatus(
            @RequestParam("orderId") Integer orderId,
            @RequestParam("paymentStatus") PaymentStatus paymentStatus) {
        OrderEntity orderEntity = orderService.updatePaymentStatus(orderId, paymentStatus);
        OrderEntityDto orderEntityDto = orderService.createOrderEntityDtoByOrderEntity(orderEntity);
        return ResponseEntity.ok(new ApiResponse(orderEntityDto, "set payment status success"));
    }

    @PostMapping("/list")
    public ResponseEntity<Object> listOrder(@PageableDefault Pageable pageable,
                                            @RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus) {
        System.out.println("paymentStatus: " + paymentStatus);
        PageModel<OrderEntityDto> pageModel = orderService.findAllOrderEntity(pageable, paymentStatus);
        return ResponseEntity.ok(new ApiResponse(pageModel, "get list order success"));
    }
}
