/**
 * 
 */
package org.yash.yashtalks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yash.yashtalks.entity.User;

/**
 * @author tanay.ojha
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	
}
