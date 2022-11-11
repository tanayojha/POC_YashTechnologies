/**
 * @author tanay.ojha
 */

package org.yash.yashtalks.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yash.yashtalks.entity.Comment;
import org.yash.yashtalks.entity.Post;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {
    //List<Comment> findByPost(Post post, PageRequest dateCreated);

    List<Post> findPostByAuthorIdOrderById(Integer userId);

}
