/**
 * @author tanay.ojha
 */

package org.yash.yashtalks.service;

import org.springframework.web.multipart.MultipartFile;
import org.yash.yashtalks.entity.Comment;
import org.yash.yashtalks.entity.Post;
import org.yash.yashtalks.payload.CommentResponse;

import java.util.List;

public interface PostService {

    Post createNewPost(String content, MultipartFile postPhoto);

    Post savePost(int author_id, String content) throws NullPointerException;

    Post getPostById(long post_id);

    //void deleteComment(Long commentId);

    List<Post> getPostsOfUser(Integer author_id);

    // Read or fetch Post list
    List<Post> getPostsList();

    Comment saveComment(int user_id, long post_id, String comment);

//    void likePost(Long postId) throws Exception;

//    void unlikePost(Long postId) throws Exception;

    void deletePost(Long postId);

    //void deleteComment(Long commentId);

    List<CommentResponse> getPostCommentsPaginate(Post post);

    //List<Comment> getPostCommentsPaginate(Post post, Integer page, Integer size);

}
