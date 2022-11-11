package org.yash.yashtalks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yash.yashtalks.entity.Comment;
import org.yash.yashtalks.entity.Post;
import org.yash.yashtalks.entity.User;
import org.yash.yashtalks.entity.UserDetails;
import org.yash.yashtalks.exception.EmptyPostException;
import org.yash.yashtalks.payload.CommentResponse;
import org.yash.yashtalks.service.FileStorageService;
import org.yash.yashtalks.service.PostService;
import org.yash.yashtalks.service.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author tanay.ojha
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @PostMapping("/createnewpost")
    public ResponseEntity<?> createNewPost(@RequestParam(value = "content", required = false) Optional<String> content,
                                           @RequestParam(name = "postphoto", required = false) Optional<MultipartFile> postPhoto)
                                            throws JsonProcessingException {
        if ((content.isPresent() || content.get().length() <= 0) &&
                (postPhoto.isPresent() || postPhoto.get().getSize() <= 0)) {
            throw new EmptyPostException("EmptyPostException");
        }

        ObjectMapper mapper = new ObjectMapper();
        String contentToAdd = content.isPresent() ? null : content.get();
        MultipartFile postPhotoToAdd = postPhoto.isPresent() ? null : postPhoto.get();

        Post createdPost = postService.createNewPost(contentToAdd, postPhotoToAdd);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PostMapping("/addpost/{author_id}")
    public ResponseEntity<?> addPost(@RequestBody Post post, @PathVariable int author_id)
            throws NullPointerException {
        Optional<User> user = Optional.ofNullable(userService.getAuthenticatedUser());
        logger.info("Post Controller -> add post -> user ",user);
        Post savedPost = postService.savePost(author_id,post.getContent());
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
        logger.info("Post Controller -> add comment -> ",user);
        if(user.isPresent()){
          int user_id = user.get().getId();
            saveComment = postService.saveComment(user_id,post_id,comment);
            logger.info("Post Controller -> add comment -> ",saveComment);
        }
        return ResponseEntity.ok(saveComment);
    }

//    @PostMapping("/likepost/{post_id}/like")
//    public ResponseEntity<?> likePost(@PathVariable("post_id") Long postId) throws Exception {
//        postService.likePost(postId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping("/unlikepost/{post_id}/unlike")
//    public ResponseEntity<?> unlikePost(@PathVariable("post_id") Long postId) throws Exception{
//        postService.unlikePost(postId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @DeleteMapping("/deletepost/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable("post_id") Long post_id) {
        postService.deletePost(post_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/mypost")
    public ResponseEntity<?> myPosts() throws NullPointerException {
        User user=userService.getAuthenticatedUser();
        logger.info("Info "+user);
        List<Post> postList = postService.getPostsOfUser(user.getId());
        return ResponseEntity.ok(postList);
    }

    // REST api for get all Posts list of Users
    @GetMapping("/getallposts")
    public List<Post> getPostList() {
        // Returning value for User list
        List<Post> postlist = postService.getPostsList();
        postlist.sort((p1,p2)->p1.getId().compareTo(p2.getId()));
        Collections.reverse(postlist);
        return postlist;
    }

    @GetMapping("/getpostcomments/{post_id}")
    public ResponseEntity<?> getPostComments(@PathVariable("post_id") Long postId){
        Post targetPost = postService.getPostById(postId);
        List<CommentResponse> postCommentResponseList = postService.getPostCommentsPaginate(targetPost);
        return new ResponseEntity<>(postCommentResponseList, HttpStatus.OK);
    }

}
