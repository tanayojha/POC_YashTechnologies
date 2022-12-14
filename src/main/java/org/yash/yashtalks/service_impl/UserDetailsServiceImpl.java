 /**
 * 
 */
package org.yash.yashtalks.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yash.yashtalks.repositories.UserRepository;
import org.yash.yashtalks.entity.User;

/**
 * @author tanay.ojha
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = this.userRepository.findByUsername(username);
		
		if (user == null) {
			System.err.println("user not found");
			throw new UsernameNotFoundException("No User Found !!");
		}

		boolean enabled = user.getAccountVerified();
		
		return user;
	}

}