/**
 * 
 */
package org.yash.yashtalks.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yash.yashtalks.entity.FollowUser;
import org.yash.yashtalks.entity.Role;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserRole;
import org.yash.yashtalks.payload.BaseResponse;
import org.yash.yashtalks.service.UserService;

/**
 * @author tanay.ojha
 *
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService service;

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	// REST api for get all users list
	@GetMapping("/getalluser")
	public BaseResponse getUserList() {
		// Returning value for User list
		return service.getUserList();
	}



	@GetMapping("/getList")
	public BaseResponse getList(){
		return service.getList();
	}

	// REST api for Create User
	@PostMapping("/signup")
	public Optional<User> createUser(@Validated @RequestBody User user) throws Exception {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		logger.info(user.getUsername());
//		newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
		newUser.setPassword(user.getPassword());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setFollowerCount(0);
		newUser.setFollowingCount(0);
		newUser.setFollowing(false);
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
	@GetMapping("/getuser/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		// Returning value for getting User by id
		User user = service.getUserById(id);
		return ResponseEntity.ok(user);
	}

	// REST api update user by id
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable("id") int id, @RequestBody User user) {
		// Returning value for Updating User by id
		User updateduser = service.updateUserById(id, user);
		logger.info("updateduser",updateduser);
		return ResponseEntity.ok(updateduser);
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

	@GetMapping("/getfollowinguserid/{id}")
	public ResponseEntity<?> getFollowingUserById(@PathVariable("id") int userId) {
		List<Integer> list = service.getfollowingUserById(userId);
		return ResponseEntity.ok(list);
	}

	//REST api for unfollow to the following user
	@PostMapping("/unfollow/{id}")
	public ResponseEntity<?> unfollowUser(@PathVariable("id") int userId) {
		service.unfollowUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//REST api for follow user
	@PostMapping("/follow/{id}")
	public ResponseEntity<?> followUser(@PathVariable("id") int userId) {
		service.followUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}