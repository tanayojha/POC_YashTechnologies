/**
 * 
 */
package org.yash.yashtalks.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author tanay.ojha
 *
 */
public class Authority implements GrantedAuthority{
	
	private String authority; 

	/**
	 * 
	 */
	public Authority(String authority) {
		this.authority = authority;
		// TODO Auto-generated constructor stub
	}



	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.authority;
	}

}
