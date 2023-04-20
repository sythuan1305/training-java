package com.beetech.trainningJava;

// import com.beetech.trainningJava.entity.CartEntity;
// import com.beetech.trainningJava.repository.CartRepository;
import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.repository.AccountRepository;
import com.beetech.trainningJava.repository.CartRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class TrainningJavaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TrainningJavaApplication.class, args);
	}
}
