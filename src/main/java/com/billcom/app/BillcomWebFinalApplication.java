package com.billcom.app;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.billcom.app.entity.Role;
import com.billcom.app.repository.RoleRepository;
import com.billcom.app.service.ProjectService;
import com.billcom.app.service.UserService;

@SpringBootApplication
public class BillcomWebFinalApplication {


    @Autowired
    RoleRepository roleRepository;
    @Autowired 
    UserService userService;
	
    /*public void run(String... args) throws Exception {
    

 	   roleRepository.save(new Role(1,"manager"));
 	   roleRepository.save(new Role(2,"project leader"));
 	   roleRepository.save(new Role(3,"leader"));
 	   roleRepository.save(new Role(4,"member"));


    }
*/

	public static void main(String[] args) {
		new File(ProjectService.uploadDirectory).mkdir();
		new File(UserService.uploadDirectory).mkdir();
		new File(UserService.userDirectory).mkdir();

		SpringApplication.run(BillcomWebFinalApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
	 
	
}
