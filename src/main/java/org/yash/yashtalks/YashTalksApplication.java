package org.yash.yashtalks;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yash.yashtalks.entity.Role;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserRole;
import org.yash.yashtalks.service.UserService;

@SpringBootApplication
public class YashTalksApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {

		SpringApplication.run(YashTalksApplication.class, args);

	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		// User
//		User user = new User();
//		user.setFirstName("Admin");
//		user.setLastName("admin");
//		user.setUsername("admin@yash.com");
//		user.setPassword("admin");
//
//		// Admin role
//		Role role1 = new Role();
//		role1.setRoleId(1);
//		role1.setRoleName("ADMIN");
//		Set<UserRole> userRoleSet = new HashSet<>();
//		UserRole userRole = new UserRole();
//		userRole.setRole(role1);
//		userRole.setUser(user);
//		userRoleSet.add(userRole);

		//Optional<User> newuser = this.userService.insertUser(user, userRoleSet);

		//System.out.println(newuser.get().getUsername());

	}

}
