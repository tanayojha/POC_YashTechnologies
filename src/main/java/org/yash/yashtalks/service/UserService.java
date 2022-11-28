/**
 * 
 */
package org.yash.yashtalks.service;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.yash.yashtalks.entity.FollowUser;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserRole;
import org.yash.yashtalks.payload.BaseResponse;
import org.yash.yashtalks.payload.UserResponse;

/**
 * @author tanay.ojha
 *
 */
public interface UserService {

	// Create Record of a User or Insert User data
	public Optional<User> insertUser(User user, Set<UserRole>userRole) throws Exception;

	// Read or fetch User list
	public BaseResponse getUserList();

	BaseResponse getList();

	// Update a user record on basis of user Id
	public User updateUserById(int userId, User user);

	// Delete a user record on basis of Id
	public ResponseEntity<Map<String, String>> deleteUserById(int userId);

	// Get a User record on basis of Id
	public User getUserById(int author_id);

	// Delete All User record
	public Map<String, String> deleteAllUser();

	//For User Authentication
	public User getAuthenticatedUser();

	//For Getting UserName i.e email
	User getUserByEmail(String email);

	//For following user on click Follow
	void followUser(int userId);

	//For unfollowing user on click following
	void unfollowUser(int userId);

	//List<UserResponse> getFollowerUsers(Long userId);

	List<Integer> getfollowingUserById(Integer user_id);

}
