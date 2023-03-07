package com.beetech.trainningJava;

import com.beetech.trainningJava.entity.AccountEntity;
// import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.repository.AccountRepository;
// import com.beetech.trainningJava.repository.CartRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class TrainningJavaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TrainningJavaApplication.class, args);

//		AccountEntity account = new AccountEntity();
//		CartEntity cart = new CartEntity();
//		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
//		AccountRepository accountRepository = context.getBean(AccountRepository.class);
//		CartRepository cartRepository = context.getBean(CartRepository.class);
//
//		cart.setTotalQuantity(0);
//		cart.setTotalPrice(BigDecimal.valueOf(0.00));
//		CartEntity cart1 = cartRepository.save(cart);
//		System.out.println(cart1.getId());
//
//		account.setUsername("admin");
//		account.setPassword(passwordEncoder.encode("123456"));
//		account.setEmail("thuanbui1305@gmail.com");
//		account.setVerify(true);
//		account.setBlocked(false);
//		account.setRole(Role.ADMIN);
//		account.setCartId(cart1.getId());
////		System.out.println(accountRepository.save(account).getId());
//		accountRepository.save(account);
	}

}
