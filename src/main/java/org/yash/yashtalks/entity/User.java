/**
 * 
 */
package org.yash.yashtalks.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tanay.ojha
 *
 */

@Entity
@Table(name = "user")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "mobile")
	private String mobile;	
	
	@Column(name = "profile")
	private String profile;
	
	@Column(name = "enabled")
	private boolean enabled = true;
	
	//User can have many roles
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	

	
	/**
	 * @param id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param mobile
	 * @param profile
	 * @param enabled
	 * @param userRoles
	 */
	public User(int id, String username, String password, String firstName, String lastName, String mobile,
			String profile, boolean enabled, Set<UserRole> userRoles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.profile = profile;
		this.enabled = enabled;
		this.userRoles = userRoles;
	}




	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		Set<Authority>set =new HashSet<>();
		
		this.userRoles.forEach(userRole ->{
			set.add(new Authority(userRole.getRole().getRoleName()));
		});
		
		return set;
	}

	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

 
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}





	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}





	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}





	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}





	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}





	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}



	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}



	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}



	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}





	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}





	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}





	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}





	/**
	 * @return the userRoles
	 */
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}





	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}





	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", password=" + password + ", mobile=" + mobile + "]";
	}


	
}
