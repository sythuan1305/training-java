package com.beetech.trainningJava.controller.api.user;

import com.beetech.trainningJava.model.*;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.service.IProductDiscountConditionService;
import com.beetech.trainningJava.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@RestController(value = "apiOfUserDiscountController")
@RequestMapping(value = "/api/user/discount")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DiscountController {
    private final ICartProductService cartProductService;

    private final IProductDiscountConditionService discountProductService;

    /**
     * Chuyển đến trang xem danh sách giảm giá
     *
     * @param cookieValue   giá trị của cookie cart
     * @param cartProductId danh sách id sản phẩm trong giỏ hàng được chọn để mua
     * @return trang xem danh sách giảm giá
     */
    @PostMapping("/list")
    public ResponseEntity<Object> information(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue,
            @RequestParam("cartProductId") Long[] cartProductId) {
        System.out.println("cookieValue: " + cookieValue);
        System.out.println(Utils.JsonParserListObjectWithEncodedURL(cookieValue));
        // Lấy thông tin giỏ hàng từ cookie hoặc database

        List<CartProductEntityDto> cartProductEntityDtos = cartProductService.createCartProductEntityDtoListWithLoginOrNotWithCartProductParserList(
                Utils.JsonParserListObjectWithEncodedURL(cookieValue)
        );

        // Lọc ra các sản phẩm được chọn để mua
        cartProductEntityDtos = cartProductService.getCartProductEntityDtoListByCartProductEntityDtoListAndCartProductArray(
                cartProductEntityDtos, cartProductId);

        // Lấy danh sách giảm giá
        List<DiscountEntityDto> discountEntityDtos = discountProductService
                .getDiscountEntityDtoListByCartProductInforModelList(cartProductEntityDtos);

        return ResponseEntity.ok(new ApiResponse(discountEntityDtos, "get discount success"));
    }

    @PostMapping("/test")
    public ResponseEntity<Object> test(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue) {
        return ResponseEntity.ok(cookieValue);
    }
}
