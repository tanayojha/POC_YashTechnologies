/**
 * 
 */
package org.yash.yashtalks.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserRole;

/**
 * @author tanay.ojha
 *
 */
public interface UserService {

	// Create Record of a User or Insert User data
	public Optional<User> insertUser(User user, Set<UserRole>userRole) throws Exception;

	// Read or fetch User list
	public Optional<List<User>> getUserList();

	// Update a user record on basis of Id
	public ResponseEntity<User> updateUserById(int id, User user);

	// Delete a user record on basis of Id
	public ResponseEntity<Map<String, String>> deleteUserById(int id);

	// Get a User record on basis of Id
	public ResponseEntity<User> getUserById(int id);

	// Delete All User record
	public Map<String, String> deleteAllUser();
	
}
