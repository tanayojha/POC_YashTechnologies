/**
 * 
 */
package org.yash.yashtalks.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tanay.ojha
 *
 */
@Entity
@Table(name = "user_role")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_role_id")
	private int userRoleId;


	// Many User can have one role at a time
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JsonIgnore
	private User user;

	// Many Roles can have one user at a time
	@ManyToOne()
	@JsonIgnore
	private Role role;

	/**
	 * 
	 */
	public UserRole() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userRoleId
	 * @param user
	 * @param role
	 */
	public UserRole(int userRoleId, User user, Role role) {
		super();
		this.userRoleId = userRoleId;
		this.user = user;
		this.role = role;
	}

	/**
	 * @return the userRoleId
	 */
	public int getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	

	
}
