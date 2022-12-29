package org.yash.yashtalks.service_impl;

/**
 * @author tanay.ojha
 */

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yash.yashtalks.entity.Comment;
import org.yash.yashtalks.exception.PostNotFoundException;
import org.yash.yashtalks.payload.CommentResponse;
import org.yash.yashtalks.repositories.CommentRepository;
import org.yash.yashtalks.repositories.PostsRepository;
import org.yash.yashtalks.repositories.UserRepository;
import org.yash.yashtalks.entity.Post;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.service.PostService;
import org.yash.yashtalks.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostsRepository postRepository;

    @Autowired
    CommentRepository commentRepository;


    @Override
    public List<Post> getPostsList() {
        // Returning value for User list
        return postRepository.findAll();
    }

    @Override
    public Post createNewPost(String post, MultipartFile postPhoto) throws IOException {
        User authUser = userService.getAuthenticatedUser();
        Post newPost = new Post();
        newPost.setContent(post);
        newPost.setAuthor(authUser);
        newPost.setLikeCount(0);
        newPost.setShareCount(0);
        newPost.setCommentCount(0);
        newPost.setIsTypeShare(false);
        newPost.setDateCreated(new Date());
        newPost.setPostPhoto(postPhoto.getBytes());
        newPost.setDateLastModified(new Date());
        logger.info("newPost",newPost);
        return postRepository.save(newPost);
    }

    public Post savePost(int author_id, String content, String image) throws NullPointerException{
        Post post = new Post();
        User user = userService.getUserById(author_id);
        post.setAuthor(user);
        post.setCommentCount(0);
        post.setContent(content);
        post.setIsTypeShare(true);
        post.setLikeCount(0);
        //post.setPostPhoto(image);
        post.setShareCount(0);
        logger.info("PostServiceImpl -> savePost()-> ",post);
        // Returning save Post Object
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(long post_id) {
        /*
         * Checking If Post Exist in database
         */
        Post post = postRepository.findById(post_id)
        .orElseThrow(() -> new PostNotFoundException("Post Data Not Exist With " + "ID -> " + post_id));

        // Loggers
        logger.info("Getting Post BY ID ->" + post);

        // Returning value for get post by id
        return post;
    }

    @Override
    public Comment saveComment(int user_id, long post_id, String content) throws NullPointerException{
        Comment comment = new Comment();
        User author = userService.getUserById(user_id);
        comment.setAuthor(author);
        Post post = getPostById(post_id);
        comment.setPost(post);
        comment.setContent(content);
        comment.setLikeCount(0);
        comment.setLikeList(null);
        logger.info("PostServiceImpl -> saveComment() -> ",comment);
        // Returning save Comment Object
        return commentRepository.save(comment);
    }


    @Override
    public List<Post> getPostsOfUser(Integer userId){
        // List<Post> findPostByUserOrderById(Integer author_id);
        //List<Post> postList= postRepository.findPostByUserOrderById(userRepository.findUserById(userId));
        List<Post> postList = this.postRepository.findPostByAuthorIdOrderById(userId);
        logger.info("postList",postList);
        List<Post> postDtoList= new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Post post :postList) {
            postDtoList.add(modelMapper.map(post,Post.class));
        }
        //Returning list of posts of a Particular user
        logger.info("postDtoList",postDtoList);
        return postDtoList;
    }

    @Override
    public List<CommentResponse> getPostComments(Post post) {
        User authUser = userService.getAuthenticatedUser();
        List<Comment> foundCommentList = commentRepository.findByPost(post);
        logger.info("foundCommentList",foundCommentList);

        List<CommentResponse> commentResponseList = new ArrayList<>();
        logger.info("commentResponseList",commentResponseList);

        foundCommentList.forEach(comment -> {
            CommentResponse newCommentResponse = CommentResponse.builder()
                    .comment(comment)
                    .build();
            commentResponseList.add(newCommentResponse);
        });

        return commentResponseList;
    }

    @Override
    public Post likePost(Long postId) throws Exception {
        User authUser = userService.getAuthenticatedUser();
        System.out.println("authUser = " + authUser);
        Post targetPost = getPostById(postId);
        System.out.println("authUser = " + authUser);
        if(targetPost.getLikeList().contains(authUser)){
            System.out.println("getUnLikeList..."+targetPost.getLikeList().toString());
        }

        if (!targetPost.getLikeList().contains(authUser)) {
            targetPost.setLikeCount(targetPost.getLikeCount() + 1);
            targetPost.getLikeList().add(authUser);
            targetPost.setIsLiked(true);
            logger.info("PostServiceImpl -> likePost() -> ",targetPost);
            // Returning none reflections will be updated on database
            Post updatedPost = postRepository.save(targetPost);
            return updatedPost;
        }else {
            targetPost.setLikeCount(targetPost.getLikeCount()-1);
            targetPost.getLikeList().remove(authUser);
            targetPost.setIsLiked(false);
            logger.info("PostServiceImpl -> unlikePost() -> ",targetPost);
            // Returning none reflections will be updated on database
            Post updatedPost = postRepository.save(targetPost);
            return updatedPost;
        }
    }


    @Override
    public Post unlikePost(Long postId) throws Exception{
        User authUser = userService.getAuthenticatedUser();
        Post targetPost = getPostById(postId);
        if (targetPost.getLikeList().contains(authUser)) {
            targetPost.setLikeCount(targetPost.getLikeCount()-1);
            targetPost.getLikeList().remove(authUser);
            targetPost.setIsLiked(false);
            logger.info("PostServiceImpl -> unlikePost() -> ",targetPost);
            // Returning none reflections will be updated on database
           Post updatedPost = postRepository.save(targetPost);
           return updatedPost;
        }else{
            targetPost.setLikeCount(targetPost.getLikeCount() + 1);
            targetPost.getLikeList().add(authUser);
            targetPost.setIsLiked(true);
            logger.info("PostServiceImpl -> likePost() -> ",targetPost);
            // Returning none reflections will be updated on database
            Post updatedPost = postRepository.save(targetPost);
            return updatedPost;
        }

    }
}
