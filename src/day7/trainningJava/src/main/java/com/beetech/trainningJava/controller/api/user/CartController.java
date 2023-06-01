package com.beetech.trainningJava.controller.api.user;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.CartProductEntityDto;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.mappper.CartProductEntityDtoMapper;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.service.IProductDiscountConditionService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

/**
 * Controller này dùng để xử lý các request liên quan đến giỏ hàng với quyền user
 */
@RestController("apiCartController")
@RequestMapping("/api/user/cart")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartController {
    private final ICartProductService cartProductService;

    private final IProductService productService;

    private final IAccountService accountService;

    private final IProductDiscountConditionService productDiscountConditionService;

    private final CartProductEntityDtoMapper cartProductEntityDtoMapper;

    @PostMapping("/information-cart")
    public ResponseEntity<Object> informationCart(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue) {
        // Lấy thông tin giỏ hàng từ cookie hoặc database
        List<CartProductEntityDto> cartProductEntityDtos = cartProductService.createCartProductEntityDtoListWithLoginOrNotWithCartProductParserList(
                Utils.JsonParserListObjectWithEncodedURL(cookieValue)
        );

        return ResponseEntity.ok(new ApiResponse(cartProductEntityDtos, "get cart information success"));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> add(
            @RequestBody CartProductEntityDto cartProductEntityDto) throws ParseException {
        // Nếu chưa đăng nhập thì chuyển hướng đến trang thông tin giỏ hàng
        // và lưu giá trị của cart json vào cookie
        if (!accountService.isLogin()) {
            throw new RuntimeException("You must login to add product to cart");
        }

        // Nếu đã đăng nhập thì lưu giá trị của cart json vào database
        CartProductEntityDto cartProductEntityDtoRes = cartProductService
                .saveCartProductEntityWithAuthenticatedByCartProductEntityDto(cartProductEntityDto);
        return ResponseEntity.ok(new ApiResponse(cartProductEntityDtoRes, "add product to cart success"));
    }
}
