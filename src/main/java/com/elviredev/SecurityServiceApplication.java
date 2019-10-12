package com.elviredev;

import com.elviredev.entities.AppRole;
import com.elviredev.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class SecurityServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(SecurityServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AccountService accountService) {
		return args -> {
			// ajout de 2 roles
			accountService.saveAppRole(new AppRole(null, "USER"));
			accountService.saveAppRole(new AppRole(null, "ADMIN"));

			// ajout utilisateurs
			Stream.of("user1", "user2", "user3", "admin").forEach(username -> {
				accountService.saveAppUser(username, "1234", "1234");
			});

			// ajout du role admin à l'admin
			accountService.addRoleToUser("admin", "ADMIN");
		};
	}

	@Bean
	BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}

}
