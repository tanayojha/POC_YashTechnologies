/**
 * 
 */
package org.yash.yashtalks.entity;

/**
 * @author tanay.ojha
 *
 */
public class JWTResponse {
	
	private String token;


	/**
	 * 
	 */
	public JWTResponse() {
	
	}

	/**
	 * @param token
	 */
	public JWTResponse(String token) {
		super();
		this.token = token;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
