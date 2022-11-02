package com.ltiinfotech.recruitment;

import com.ltiinfotech.recruitment.config.AppConstants;
import com.ltiinfotech.recruitment.model.Role;
import com.ltiinfotech.recruitment.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RecruitmentApplication implements CommandLineRunner {

	@Autowired
	public RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		if(this.roleRepository.count() == 0){
			try {
				Role userRole = new Role();
				userRole.setId(AppConstants.USER_ROLE_ID);
				userRole.setName("ROLE_USER");

				Role adminRole = new Role();
				adminRole.setId(AppConstants.ADMIN_ROLE_ID);
				adminRole.setName("ROLE_ADMIN");

				List<Role> roles = new ArrayList<>();
				roles.add(userRole);
				roles.add(adminRole);

				this.roleRepository.saveAll(roles);

			}catch (Exception e){

				System.out.println(e);

			}
		}

	}
}
