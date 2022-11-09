/**
 * 
 */
package org.yash.yashtalks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yash.yashtalks.entity.Role;

/**
 * @author tanay.ojha
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
