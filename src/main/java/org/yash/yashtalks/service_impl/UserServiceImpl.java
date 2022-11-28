/**
 * 
 */
package org.yash.yashtalks.service_impl;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.yash.yashtalks.entity.FollowUser;
import org.yash.yashtalks.exception.InvalidOperationException;
import org.yash.yashtalks.payload.BaseResponse;
import org.yash.yashtalks.payload.UserResponse;
import org.yash.yashtalks.repositories.RoleRepository;
import org.yash.yashtalks.repositories.UserRepository;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserRole;
import org.yash.yashtalks.exception.UserNotFoundException;
import org.yash.yashtalks.service.UserService;

/**
 * @author tanay.ojha
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

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
	public User getUserByEmail(String email) {
		User user = userRepository.findByUsername(email);
		logger.info("user",user);
		return user;
	}

	public User getAuthenticatedUser() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("user",user);
		return getUserByEmail(user.getUsername());
	}



	@Override
	public Optional<User> insertUser(User user, Set<UserRole> userRole) throws Exception {
		
		/*
		 * Checking If User Already Registered in database
		 */
		User registeredUser = this.userRepository.findByUsername(user.getUsername());
		logger.info("registeredUser",registeredUser);

		if(registeredUser!=null) {
			// Loggers
			logger.info("registeredUser",registeredUser);
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
	public BaseResponse getUserList() {
		BaseResponse base = new BaseResponse();
		// Authenticating Logged-In User
		User authUser = getAuthenticatedUser();
		// Fetching all Registered User List
		List<User> userList  = userRepository.findAll();
		//Creating Object of that user list which are followed by LoggedIn User.
		List<User> followList = new ArrayList<>();
		//Iterating UserList
		for(User user : userList){
			//User f = new User();
			//Here, we are getting user List from database which is followed by LoggedIn User
			if(authUser.getFollowingUsers().contains(user)){
				//To distinguish user is followed by LoggedIn user set status 1
				//And 0 for non followed user.
				user.setFollow_status(1);
			}

			if(authUser.getFollowingUsers().contains(user)){
				System.out.println("getFollowingUsers..."+user.getId());
			}

			//f.setUser(user);
			followList.add(user);
		}

		logger.info("GET ALL USER LIST",followList);
		base.setData("Success",200,followList);
		// Returning value for User list
		return base;
	}

	@Override
	public BaseResponse getList() {
		BaseResponse baseResponse = new BaseResponse();
		List<User> list = userRepository.findAll();
		List<User> checkedList = list.stream().filter(Objects::isNull).collect(Collectors.toList());
		baseResponse.setData("Success",200,checkedList);
		return baseResponse;
	}


	@Override
	public User getUserById(int id) {
		/*
		 * Checking If User Exist in database
		 */
		User users = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Data Not Exist With " + "ID -> " + id));

		// Loggers
		logger.info("GETTING USER BY ID ",users);

		// Returning value for Get User by id
		return users;
	}

	@Override
	public User updateUserById(int id, User user) {
		/*
		 * Check If User Exist in database
		 */
		User userExist = userRepository.findById(id)
		.orElseThrow(() -> new UserNotFoundException("User Data Not Exist With " + "ID -> " + id));
		userExist.setFirstName(user.getFirstName());
		userExist.setLastName(user.getLastName());
		userExist.setMobile(user.getMobile());

		/*
		 * Update User into database
		 */
		User updatedUser = userRepository.save(userExist);

		// Loggers

		System.out.println("UPDATED USER BY ID ->" + updatedUser);
		// Returning value for Updating User by id
		return updatedUser;
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
		logger.info("DELETED USER BY ID ",response);

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
		logger.info("DELTED ALL USER RECORD", response.toString());

		return response;
	}

	@Override
	public void followUser(int userId) {
		//logged in user check
		User authUser = getAuthenticatedUser();
		if(authUser.getId()!=userId){
			User userToFollow = getUserById(userId);
			if (!authUser.getFollowingUsers().contains(userToFollow)) {
				authUser.getFollowingUsers().add(userToFollow);
				authUser.setFollowingCount(authUser.getFollowingCount() + 1);
				userToFollow.getFollowerUsers().add(authUser);
				authUser.setFollowing(true);
				userToFollow.setFollowing(true);
				userToFollow.setFollowerCount(userToFollow.getFollowerCount() + 1);
				logger.info("userToFollow", userToFollow);
				logger.info("authUser", authUser);
				userRepository.save(userToFollow);
				userRepository.save(authUser);
			}
		} else {
			throw new InvalidOperationException();
		}
	}


	@Override
	public void unfollowUser(int userId) {
		User authUser = getAuthenticatedUser();
		if (authUser.getId()!=userId) {
			User userToUnfollow = getUserById(userId);
			authUser.getFollowingUsers().remove(userToUnfollow);
			authUser.setFollowingCount(authUser.getFollowingCount() - 1);
			authUser.setFollowing(false);
			userToUnfollow.setFollowing(false);
			userToUnfollow.getFollowerUsers().remove(authUser);
			userToUnfollow.setFollowerCount(userToUnfollow.getFollowerCount() - 1);
			logger.info("userToUnfollow",userToUnfollow);
			logger.info("authUser",authUser);
			userRepository.save(userToUnfollow);
			userRepository.save(authUser);
		} else {
			throw new InvalidOperationException();
		}
	}

	@Override
	public List<Integer> getfollowingUserById(Integer user_id) {
		User authUser = getAuthenticatedUser();
		List<Integer> list=new ArrayList<>();
		//HashMap<String,Integer> map = new HashMap<String,Integer>();
		//map.put("user_id",user_id);
		if (authUser.getId() == user_id) {
			list = userRepository.findFollowingUserById(user_id);
		}
		return list;
	}

	private UserResponse userToUserResponse(User user) {
		User authUser = getAuthenticatedUser();
		return UserResponse.builder()
				.user(user)
				.followedByAuthUser(user.getFollowerUsers().contains(authUser))
				.build();
	}



}