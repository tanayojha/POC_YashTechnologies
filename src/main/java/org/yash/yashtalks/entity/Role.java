/**
 * 
 */
package org.yash.yashtalks.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tanay.ojha
 *
 */
@Entity
@Table(name = "role")
public class Role {

	@Id
	@Column(name = "role_id")
	private int roleId;

	@Column(name = "role_name")
	private String roleName;

	// One Role can have many users
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	/**
	 * 
	 */
	public Role() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param roleId
	 * @param roleName
	 * @param userRoles
	 */
	public Role(int roleId, String roleName, Set<UserRole> userRoles) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.userRoles = userRoles;
	}

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	
	
	

}
