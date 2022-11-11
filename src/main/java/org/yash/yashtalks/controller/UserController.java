/**
 * 
 */
package org.yash.yashtalks.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yash.yashtalks.entity.Role;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserRole;
import org.yash.yashtalks.service.UserService;

/**
 * @author tanay.ojha
 *
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;

	/**
	 * 
	 */
	public UserController() {
		// TODO Auto-generated constructor stub
	}

	// REST api for get all users list
	@GetMapping("/getalluser")
	public Optional<List<User>> getUserList() {
		// Returning value for User list
		return service.getUserList();
	}

	// REST api for Create User
	@PostMapping("/signup")
	public Optional<User> createUser(@Validated @RequestBody User user) throws Exception {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		System.out.println("INPUT-> "+user.getUsername());
//		newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
		newUser.setPassword(user.getPassword());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setFollowerCount(0);
		newUser.setFollowingCount(0);
		newUser.setEnabled(true);
		newUser.setAccountVerified(false);
		newUser.setEmailVerified(false);
		newUser.setJoinDate(new Date());
		newUser.setDateLastModified(new Date());
		//Roles
		Set<UserRole> userRole1 = new HashSet<>();
		Role role = new Role();
		role.setRoleId(2);
		role.setRoleName("NORMAL");

		UserRole userRole2 = new UserRole();
		userRole2.setRole(role);
		userRole2.setUser(newUser);

		userRole1.add(userRole2);

		newUser.setUserRoles(userRole1);

		// Returning value for inserted User
		return service.insertUser(newUser, userRole1);
	}

	// REST api get User by id
	@GetMapping("/get-user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		// Returning value for getting User by id
		return service.getUserById(id);
	}

	// REST api update user by id
	@PutMapping("/update-user/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable int id, @Validated @RequestBody User user) {
		// Returning value for Updating User by id
		return service.updateUserById(id, user);
	}

	// REST api delete user by id
	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable int id) {
		// Returning value for Deleting User by id
		return service.deleteUserById(id);
	}
	
	// REST api delete all user record
	@DeleteMapping("/deleterecord")
	public Map<String, String> deleteUserRecord() {
		// Returning value for Deleting User by id
		return service.deleteAllUser();
	}

}
