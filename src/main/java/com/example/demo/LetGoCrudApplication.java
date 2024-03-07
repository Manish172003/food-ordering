package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;

//import com.example.demo.dao.UserRepository;
//import com.example.demo.entities.User;

@SpringBootApplication
@CrossOrigin("http://localhost:3000/")
public class LetGoCrudApplication {

	public static void main(String[] args) {
		
	   SpringApplication.run(LetGoCrudApplication.class, args);
//		
//		UserRepository userrepo = context.getBean(UserRepository.class);
//		
//		User user1 = new User(12,"Manish");
//		
//		User user2 = new User(13,"Badri");
//		
//		List<User> name = new ArrayList<>();
//		name.add(user1);
//		name.add(user2);
//		
//		userrepo.saveAll(name);
	}

}
