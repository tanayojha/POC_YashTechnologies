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

    @PostMapping(value= {"/createnewpost"},
    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createNewPost(
            @RequestPart MultipartFile file,
            @RequestParam String content)
            throws IOException {

        Post createdPost = postService.createNewPost(content,file);
        logger.info("Post",String.valueOf(createdPost));

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }



    @PostMapping("/addpost/{author_id}")
    public ResponseEntity<?> addPost(@RequestBody Post post, @PathVariable int author_id)
            throws NullPointerException {
        Optional<User> user = Optional.ofNullable(userService.getAuthenticatedUser());
        logger.info("Post Controller -> add post -> user ",user);
        Post savedPost = postService.savePost(author_id,post.getContent(),null);
        logger.info("Post Controller -> add post -> post ",savedPost);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/getpostbyid/{post_id}")
    public ResponseEntity<Post> getPostById(@PathVariable long post_id) {
        // Returning value for getting Post by id
        Post post = postService.getPostById(post_id);
        logger.info("Post Controller -> get post By Id-> ",post);
        return ResponseEntity.ok(post);
    }


    @PostMapping("/addcomment/{post_id}")
    public ResponseEntity<?> addComment(@PathVariable long post_id, @RequestBody String comment) {
        Comment saveComment = null;
        Optional<User> user = Optional.ofNullable(userService.getAuthenticatedUser());
        logger.info("Post Controller -> add comment -> ", user);
        if (user.isPresent()) {
            int user_id = user.get().getId();
            saveComment = postService.saveComment(user_id, post_id, comment);
            logger.info("Post Controller -> add comment -> ", saveComment);
        }
        return ResponseEntity.ok(saveComment);
    }


    @PostMapping("/likepost/{id}")
    public ResponseEntity<?> likePost(@PathVariable("id") Long postId) throws Exception {
        Post p = postService.likePost(postId);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/unlikepost/{id}")
    public ResponseEntity<?> unlikePost(@PathVariable("id") Long postId) throws Exception{
        Post p = postService.unlikePost(postId);
        return ResponseEntity.ok(p);
    }


    //delete post reset api
    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePostById(@PathVariable Long id) {
        Post post = postsRepository.findById(id).orElseThrow( ()-> new PostNotFoundException("Post Data not Exist with id -> "+id));
        logger.info("Post",post);
        postsRepository.delete(post);
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/mypost")
    public ResponseEntity<?> myPosts() throws NullPointerException {
        User user=userService.getAuthenticatedUser();
        logger.info("Info "+user);
        List<Post> postList = postService.getPostsOfUser(user.getId());
        logger.info("postList "+postList);
        return ResponseEntity.ok(postList);
    }

    // REST api for get all Posts list of Users
    @GetMapping("/getallposts")
    public List<Post> getPostList() {
        // Returning value for User list
        List<Post> postlist = postService.getPostsList();
        postlist.sort((p1,p2)->p1.getId().compareTo(p2.getId()));
        Collections.reverse(postlist);
        logger.info("postlist",postlist);
        return postlist;
    }

    @GetMapping("/getpostcomments/{post_id}")
    public ResponseEntity<?> getPostComments(@PathVariable("post_id") Long postId){
        Post targetPost = postService.getPostById(postId);
        logger.info("Post",targetPost);
        List<CommentResponse> postCommentResponseList = postService.getPostCommentsPaginate(targetPost);
        logger.info("postCommentResponseList",postCommentResponseList);
        return new ResponseEntity<>(postCommentResponseList, HttpStatus.OK);
    }

}
