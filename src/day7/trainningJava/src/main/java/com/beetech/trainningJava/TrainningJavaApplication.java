package com.beetech.trainningJava;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.repository.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TrainningJavaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TrainningJavaApplication.class, args);

//		AccountEntity account = new AccountEntity();
//		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
//		AccountRepository accountRepository = context.getBean(AccountRepository.class);
//		account.setUsername("admin");
//		account.setPassword(passwordEncoder.encode("123456"));
//		account.setEmail("thuanbui1305@gmail.com");
//		account.setVerify(true);
//		account.setBlocked(false);
//		account.setRole(Role.ADMIN);
//		accountRepository.save(account);
	}

}
