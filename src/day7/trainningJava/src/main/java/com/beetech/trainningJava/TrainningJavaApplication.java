package com.beetech.trainningJava;

import com.beetech.trainningJava.entity.AccountEntity;
// import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.repository.AccountRepository;
// import com.beetech.trainningJava.repository.CartRepository;
import com.beetech.trainningJava.repository.CartRepository;
import com.beetech.trainningJava.service.IAuthService;
import com.beetech.trainningJava.service.ICartService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.service.imp.CartServiceImp;
import com.beetech.trainningJava.service.imp.ProductServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class TrainningJavaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TrainningJavaApplication.class, args);
//
//		AccountEntity account = new AccountEntity();
		CartEntity cart = new CartEntity();
//		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
//		AccountRepository accountRepository = context.getBean(AccountRepository.class);
		CartRepository cartRepository = context.getBean(CartRepository.class);
//
//		cart.setTotalQuantity(0);
//		cart.setTotalPrice(BigDecimal.valueOf(0.00));
////
//		account.setUsername("admin");
//		account.setPassword(passwordEncoder.encode("123456"));
//		account.setEmail("thuanbui1305@gmail.com");
//		account.setVerify(true);
//		account.setBlocked(false);
//		account.setRole(Role.ADMIN);
////		account.setCartId(cart1.getId());
////		System.out.println(accountRepository.save(account).getId());
//		accountRepository.save(account);
//		cart.setAccountId(account.getId());
		cartRepository.save(cart);

//		ICartService cartServiceImp = context.getBean(CartServiceImp.class);
//		IProductService productServiceImp = context.getBean(ProductServiceImp.class);
////		IAuthService authService = context.getBean(IAuthService.class);
//		System.out.println("product: " + productServiceImp.getOne(1));
//		System.out.println("cartId: " + cartServiceImp.getProductsByCartId(1));
	}

}
