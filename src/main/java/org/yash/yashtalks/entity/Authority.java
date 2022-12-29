/**
 * 
 */
package org.yash.yashtalks.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author tanay.ojha
 *
 */


@NoArgsConstructor
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
