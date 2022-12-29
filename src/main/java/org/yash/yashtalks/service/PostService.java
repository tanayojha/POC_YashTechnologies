/**
 * @author tanay.ojha
 */

package org.yash.yashtalks.service;

import org.springframework.web.multipart.MultipartFile;
import org.yash.yashtalks.entity.Comment;
import org.yash.yashtalks.entity.Post;
import org.yash.yashtalks.payload.CommentResponse;

import java.io.IOException;
import java.util.List;

public interface PostService {

    Post createNewPost(String post, MultipartFile postPhoto) throws IOException;

    Post savePost(int author_id, String content, String image) throws NullPointerException;

    Post getPostById(long post_id);

    //void deleteComment(Long commentId);

    List<Post> getPostsOfUser(Integer author_id);

    // Read or fetch Post list
    List<Post> getPostsList();

    Comment saveComment(int user_id, long post_id, String comment);

    Post likePost(Long postId) throws Exception;

    Post unlikePost(Long postId) throws Exception;

    //void deleteComment(Long commentId);

    List<CommentResponse> getPostComments(Post post);

    //List<Comment> getPostCommentsPaginate(Post post, Integer page, Integer size);

}
