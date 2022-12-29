package org.yash.yashtalks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yash.yashtalks.entity.Comment;
import org.yash.yashtalks.entity.Post;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.exception.PostNotFoundException;
import org.yash.yashtalks.payload.BaseResponse;
import org.yash.yashtalks.payload.CommentResponse;
import org.yash.yashtalks.repositories.PostsRepository;
import org.yash.yashtalks.service.PostService;
import org.yash.yashtalks.service.UserService;

import java.io.IOException;
import java.util.*;

/**
 * @author tanay.ojha
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    PostsRepository postsRepository;

    @PostMapping(value= {"/createnewpost"}, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createNewPost(@RequestPart MultipartFile file, @RequestParam String content)
    throws IOException {
        BaseResponse baseResponse = new BaseResponse();
        Post createdPost = postService.createNewPost(content,file);
        logger.info("Post",String.valueOf(createdPost));
        baseResponse.setData("Success",200,createdPost);
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @PostMapping("/addpost/{author_id}")
    public ResponseEntity<BaseResponse> addPost(@RequestBody Post post, @PathVariable int author_id)
    throws NullPointerException {
        BaseResponse baseResponse = new BaseResponse();
        Optional<User> user = Optional.ofNullable(userService.getAuthenticatedUser());
        logger.info("Post Controller -> add post -> user ",user);
        Post savedPost = postService.savePost(author_id,post.getContent(),null);
        logger.info("Post Controller -> add post -> post ",savedPost);
        baseResponse.setData("Success",200,savedPost);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/getpostbyid/{post_id}")
    public ResponseEntity<BaseResponse> getPostById(@PathVariable long post_id) {
        BaseResponse baseResponse = new BaseResponse();
        // Returning value for getting Post by id
        Post post = postService.getPostById(post_id);
        logger.info("Post Controller -> get post By Id-> ",post);
        baseResponse.setData("Success",200,post);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/addcomment/{post_id}")
    public ResponseEntity<BaseResponse> addComment(@PathVariable long post_id, @RequestBody String comment) {
        BaseResponse baseResponse = new BaseResponse();
        Comment saveComment = null;
        Optional<User> user = Optional.ofNullable(userService.getAuthenticatedUser());
        logger.info("Post Controller -> add comment -> ", user);
        if (user.isPresent()) {
            int user_id = user.get().getId();
            saveComment = postService.saveComment(user_id, post_id, comment);
            logger.info("Post Controller -> add comment -> ", saveComment);
        }
        baseResponse.setData("Success",200,saveComment);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/likepost/{id}")
    public ResponseEntity<BaseResponse> likePost(@PathVariable Long postId) throws Exception {
        BaseResponse baseResponse = new BaseResponse();
        Optional<Post> post = Optional.ofNullable(postService.likePost(postId));
        //Post p = postService.likePost(postId);
        baseResponse.setData("Success",200,post);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/unlikepost/{id}")
    public ResponseEntity<BaseResponse> unlikePost(@PathVariable("id") Long postId) throws Exception{
        BaseResponse baseResponse = new BaseResponse();
        Optional<Post> post = Optional.ofNullable(postService.unlikePost(postId));
        //Post p = postService.unlikePost(postId);
        baseResponse.setData("Success",200,post);
        return ResponseEntity.ok(baseResponse);
    }

    //delete post reset api
    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<BaseResponse> deletePostById(@PathVariable Long id) {
        BaseResponse baseResponse = new BaseResponse();
        Post post = postsRepository.findById(id).orElseThrow( ()-> new PostNotFoundException("Post Data not Exist with id -> "+id));
        logger.info("Post",post);
        postsRepository.delete(post);
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("Deleted", Boolean.TRUE);
        baseResponse.setData("Success",200,response);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/mypost")
    public ResponseEntity<BaseResponse> myPosts() throws NullPointerException {
        BaseResponse baseResponse = new BaseResponse();
        Optional<User> user = Optional.ofNullable(userService.getAuthenticatedUser());
        logger.info("Info "+user);
        List<Post> postList = postService.getPostsOfUser(user.get().getId());
        logger.info("postList "+postList);
        baseResponse.setData("Success",200,postList);
        return ResponseEntity.ok(baseResponse);
    }

    // REST api for get all Posts list of Users
    @GetMapping("/getallposts")
    public ResponseEntity<BaseResponse> getPostList() {
        //public List<Post> getPostList() {
        BaseResponse baseResponse = new BaseResponse();
        // Returning value for User list
        List<Post> postlist = postService.getPostsList();
        postlist.sort((p1,p2)->p1.getId().compareTo(p2.getId()));
        Collections.reverse(postlist);
        logger.info("postlist",postlist);
        baseResponse.setData("Success",200,postlist);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/getpostcomments/{post_id}")
    public ResponseEntity<BaseResponse> getPostComments(@PathVariable("post_id") Long postId){
        BaseResponse baseResponse = new BaseResponse();
        Post targetPost = postService.getPostById(postId);
        logger.info("Post",targetPost);
        List<CommentResponse> postCommentResponseList = postService.getPostComments(targetPost);
        logger.info("postCommentResponseList",postCommentResponseList);
        baseResponse.setData("Success",200,postCommentResponseList);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
