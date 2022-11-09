/**
 * 
 */
package org.yash.yashtalks.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestMapping;
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
		String token = this.jwtUtils.generateToken(userDetails);
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
		return ((User) this.userDetailsServiceImpl.loadUserByUsername(principal.getName()));
	}

}