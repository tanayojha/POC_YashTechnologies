/**
 * 
 */
package org.yash.yashtalks.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author tanay.ojha
 *
 */
@Entity
@Table(name = "user_details")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_details_id")
	private Integer userDetailId;

	@Column(name = "date_of_joining")
	private String dateOfJoining;

	@Column(name = "department")
	private String department;

	@Column(name = "designation")
	private String designation;

	@JoinColumn(name = "userid")
	@ManyToOne(fetch = FetchType.LAZY)
    private User user;
	
	
	/**
	 * 
	 */
	public UserDetails() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the userDetailId
	 */
	public Integer getUserDetailId() {
		return userDetailId;
	}


	/**
	 * @param userDetailId the userDetailId to set
	 */
	public void setUserDetailId(Integer userDetailId) {
		this.userDetailId = userDetailId;
	}


	/**
	 * @return the dateOfJoining
	 */
	public String getDateOfJoining() {
		return dateOfJoining;
	}


	/**
	 * @param dateOfJoining the dateOfJoining to set
	 */
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}


	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}


	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}


	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}


	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
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

	
}
