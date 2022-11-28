/**
 * 
 */
package org.yash.yashtalks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yash.yashtalks.entity.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

//	@Query(value = "Select followed_id from follow_users where follower_id =:userid", nativeQuery = true)
//	HashMap<String,Integer> followUserIdList(Integer userid);

	@Query(value = "Select  followed_id from follow_users where follower_id =:userid", nativeQuery = true)
	List<Integer> findFollowingUserById(Integer userid);
}
