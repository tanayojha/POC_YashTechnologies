package org.yash.yashtalks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yash.yashtalks.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File,String> {
}
