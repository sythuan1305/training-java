package com.beetech.trainningJava.payment.paypal.config;

import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Class nơi khởi tạo các bean cần thiết cho việc sử dụng paypal
 */
@Configuration
public class PaypalConfig {

    // clientId là id của ứng dụng paypal
    @Value("${paypal.client.id}")
    private String clientId;

    // clientSecret là secret của ứng dụng paypal
    @Value("${paypal.client.secret}")
    private String clientSecret;

    // mode là chế độ sử dụng của paypal, có 2 chế độ là sandbox và live
    @Value("${paypal.mode}")
    private String mode;

    // APIContext là class chứa các thông tin cần thiết để gọi api của paypal
    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId, clientSecret, mode);
    }

    // RedirectUrls là class chứa các url cần thiết cho việc redirect sau khi thực hiện thanh toán hoặc hủy thanh toán
    @Bean
    public RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/user/order/cancelPayment");
        redirectUrls.setReturnUrl("http://localhost:8080/user/order/executePayment");
        return redirectUrls;
    }
}
