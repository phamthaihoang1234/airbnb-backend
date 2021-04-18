package com.codegym.airbnb;

import com.codegym.airbnb.model.UserModel;
import com.codegym.airbnb.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);

	@Autowired
	private UserService userService;

	@Override
	public void run(String... strings) throws Exception {
		if (!userService.findByEmail("anhnbt@icloud.com").isPresent()) {
			UserModel user = new UserModel();
			user.setName("Nguyen Ba Tuan Anh");
			user.setUsername("anhnbt");
			user.setEmail("anhnbt@icloud.com");
			user.setPassword("123456");
			user.setPhone("+84346868928");
			user.setDateOfBirth(LocalDate.now());
			user.setGender((byte) 1);
			user.setActive(true);
			user.setCreatedDate(LocalDateTime.now());
			user.setModifiedDate(LocalDateTime.now());
			this.userService.save(user);
			logger.info("Inserting customer record for " + user.getName());
		}

	}
}
