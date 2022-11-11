/**
 * 
 */
package org.yash.yashtalks.service_impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yash.yashtalks.dao.RoleRepository;
import org.yash.yashtalks.dao.UserRepository;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserRole;
import org.yash.yashtalks.exception.UserNotFoundException;
import org.yash.yashtalks.service.UserService;

/**
 * @author tanay.ojha
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	/**
	 * 
	 */
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Optional<User> insertUser(User user, Set<UserRole> userRole) throws Exception {
		
		/*
		 * Checking If User Already Registered in database
		 */
		User registeredUser = this.userRepository.findByUsername(user.getUsername());
		if(registeredUser!=null) {
			// Loggers
			System.out.println("Registered User!");
			throw new Exception("User Already Registered.");
		}
		else {
			
			for (UserRole user_role: userRole) {
				roleRepository.save(user_role.getRole());
			}
			
			user.getUserRoles().addAll(userRole);
			
			
			// Loggers
			//System.out.println("INSERTED USER ->" + Optional.of(userRepository.save(user)));
			// Returning value for inserted User
			return Optional.of(userRepository.save(user));	
		}
	}

	@Override
	public Optional<List<User>> getUserList() {
		// Loggers
		System.out.println("GET ALL USER LIST ->" + Optional.of(userRepository.findAll()));
		// Returning value for User list
		return Optional.of(userRepository.findAll());
	}

	@Override
	public ResponseEntity<User> getUserById(int id) {
		/*
		 * Checking If User Exist in database
		 */
		User users = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Data Not Exist With " + "ID -> " + id));

		// Loggers
		System.out.println("GETTING USER BY ID ->" + users);
		// Returning value for Get User by id
		return ResponseEntity.ok(users);
	}

	@Override
	public ResponseEntity<User> updateUserById(int id, User user) {
		/*
		 * Check If User Exist in database
		 */
		User userExist = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Data Not Exist With " + "ID -> " + id));
		userExist.setFirstName(user.getFirstName());
		userExist.setLastName(user.getLastName());
		userExist.setUsername(user.getUsername());

		/*
		 * Update User into database
		 */
		User updatedUser = userRepository.save(userExist);

		// Loggers
		System.out.println("UPDATED USER BY ID ->" + updatedUser);
		// Returning value for Updating User by id
		return ResponseEntity.ok(updatedUser);
	}

	@Override
	public ResponseEntity<Map<String, String>> deleteUserById(int id) {
		/*
		 * Check If User Exist in database
		 */
		User userExist = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Data Not Exist With " + "ID -> " + id));
		/*
		 * Delete User from database
		 */
		userRepository.delete(userExist);

		// Now displaying Json response with deleted message.
		Map<String, String> response = new HashMap<String, String>();
		response.put("Message", "User with id '" + id + "' Deleted Successfully");

		// Loggers
		System.out.println("DELTED USER BY ID ->" + response);
		// returning response
		return ResponseEntity.ok(response);
	}

	@Override
	public Map<String, String> deleteAllUser() {
		//final String message = "No Record Found";
		/*
		 * Delete Total User Record from database of User Table
		 */
		userRepository.deleteAll();

		// Now displaying Json response with delete message.
		Map<String, String> response = new HashMap<String, String>();
		response.put("Message", "No Record Found");

		// Loggers
		System.out.println("DELTED ALL USER RECORD ->" + response.toString());

		return response;
	}

}
