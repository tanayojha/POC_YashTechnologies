/**
 * 
 */
package org.yash.yashtalks.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yash.yashtalks.config.JWTUtils;
import org.yash.yashtalks.entity.JWTRequest;
import org.yash.yashtalks.entity.JWTResponse;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.exception.UserNotFoundException;
import org.yash.yashtalks.service_impl.UserDetailsServiceImpl;

/**
 * @author tanay.ojha
 *
 */

@RestController
@CrossOrigin("*")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JWTUtils jwtUtils;

	// For Generate Token
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JWTRequest JwtRequest) throws Exception {
		try {
			authenticate(JwtRequest.getUsername(), JwtRequest.getPassword());
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			throw new Exception("user not found");
		}
		UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(JwtRequest.getUsername());
		logger.info("userDetails",userDetails);
		String token = this.jwtUtils.generateToken(userDetails);
		logger.info("token",token);
		return ResponseEntity.ok(new JWTResponse(token));
	}

	// For Authentication
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER DISABLED" + e.getMessage());
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials" + e.getMessage());
		}
	}

	// return details of current user (login API)
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		logger.info("Principal",principal.getName());
		return ((User) this.userDetailsServiceImpl.loadUserByUsername(principal.getName()));
	}

}