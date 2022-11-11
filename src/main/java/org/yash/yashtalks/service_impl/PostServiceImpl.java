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
import org.springframework.core.env.Environment;
import org.yash.yashtalks.service.UserService;
import org.yash.yashtalks.util.FileNamingUtil;
import org.yash.yashtalks.util.FileUploadUtil;
import java.io.File;
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

    @Autowired
    Environment environment;

    @Autowired
    FileNamingUtil fileNamingUtil;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Override
    public List<Post> getPostsList() {
        // Returning value for User list
        return postRepository.findAll();
    }



    @Override
    public Post createNewPost(String content, MultipartFile postPhoto) {
        User authUser = userService.getAuthenticatedUser();
        Post newPost = new Post();
        newPost.setContent(content);
        newPost.setAuthor(authUser);
        newPost.setLikeCount(0);
        newPost.setShareCount(0);
        newPost.setCommentCount(0);
        newPost.setIsTypeShare(false);
        //newPost.setSharedPost(null);
        newPost.setDateCreated(new Date());
        newPost.setDateLastModified(new Date());

        if (postPhoto != null && postPhoto.getSize() > 0) {
            String uploadDir = environment.getProperty("upload.post.images");
            String newPhotoName = fileNamingUtil.nameFile(postPhoto);
            String newPhotoUrl = environment.getProperty("app.root.backend") + File.separator
                    + environment.getProperty("upload.post.images") + File.separator + newPhotoName;
            newPost.setPostPhoto(newPhotoUrl);
            try {
                fileUploadUtil.saveNewFile(uploadDir, newPhotoName, postPhoto);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        return postRepository.save(newPost);
    }

    public Post savePost(int author_id, String content) throws NullPointerException{
        Post post = new Post();
        User user = userService.getUserById(author_id);
        post.setAuthor(user);
        post.setCommentCount(0);
        post.setContent(content);
        post.setIsTypeShare(true);
        post.setLikeCount(0);
        post.setPostPhoto("default.png");
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
    public void deletePost(Long postId) {
        User authUser = userService.getAuthenticatedUser();
        Post targetPost = getPostById(postId);

        if (targetPost.getAuthor().equals(authUser)) {
            targetPost.getShareList().forEach(sharingPost -> {
                sharingPost.setSharedPost(null);
                postRepository.delete(sharingPost);
            });
        }
    }

//    @Override
//    public List<Comment> getPostCommentsPaginate(Post post, Integer page, Integer size) {
//        User authUser = userService.getAuthenticatedUser();
//        List<Comment> foundCommentList = postRepository.findByPost(
//                post,
//                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated"))
//        );
//        logger.info("getPostCommentsPaginate()-> foundCommentList-> ",foundCommentList);
//
//
//        //Returning CommentList of User which post is Commented
//        return foundCommentList;
//    }

//
//    @Override
//    public void deleteComment(Long commentId) {
//        User authUser = userService.getAuthenticatedUser();
//        Comment targetComment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
//        if (targetComment.getAuthor().equals(authUser)) {
//            commentRepository.deleteById(commentId);
//        } else {
//            throw new InvalidOperationException();
//        }
//    }

//    @Override
//    public void likePost(Long postId) throws Exception {
//        User authUser = userService.getAuthenticatedUser();
//        Post targetPost = getPostById(postId);
//        if (!targetPost.getLikeList().contains(authUser)) {
//            targetPost.setLikeCount(targetPost.getLikeCount() + 1);
//            targetPost.getLikeList().add(authUser);
//            logger.info("PostServiceImpl -> likePost() -> ",targetPost);
//            // Returning none reflections will be updated on database
//            postRepository.save(targetPost);
//        } else {
//            throw new Exception();
//        }
//    }

//    @Override
//    public void unlikePost(Long postId) throws Exception{
//        User authUser = userService.getAuthenticatedUser();
//        Post targetPost = getPostById(postId);
//        if (targetPost.getLikeList().contains(authUser)) {
//            targetPost.setLikeCount(targetPost.getLikeCount()-1);
//            targetPost.getLikeList().remove(authUser);
//            logger.info("PostServiceImpl -> unlikePost() -> ",targetPost);
//            // Returning none reflections will be updated on database
//            postRepository.save(targetPost);
//        } else {
//            throw new Exception();
//        }
//    }

//            //Returning none but deleted Post from Database
//            postRepository.deleteById(postId);
//
////            if (targetPost.getPostPhoto() != null) {
////                String uploadDir = environment.getProperty("upload.post.images");
////                String photoName = getPhotoNameFromPhotoUrl(targetPost.getPostPhoto());
////                try {
////                    fileUploadUtil.deleteFile(uploadDir, photoName);
////                } catch (IOException ignored) {}
////            }
//        } else {
//            //If Operation will be failed
//            throw new InvalidOperationException();
//        }
//    }

    @Override
    public List<Post> getPostsOfUser(Integer userId){
        // List<Post> findPostByUserOrderById(Integer author_id);
        //List<Post> postList= postRepository.findPostByUserOrderById(userRepository.findUserById(userId));
        List<Post> postList = this.postRepository.findPostByAuthorIdOrderById(userId);
        List<Post> postDtoList= new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Post post :postList) {
            postDtoList.add(modelMapper.map(post,Post.class));
        }
        //Returning list of posts of a Particular user
        return postDtoList;
    }

    @Override
    public List<CommentResponse> getPostCommentsPaginate(Post post) {
        User authUser = userService.getAuthenticatedUser();
        List<Comment> foundCommentList = commentRepository.findByPost(post);

        List<CommentResponse> commentResponseList = new ArrayList<>();
        foundCommentList.forEach(comment -> {
            CommentResponse newCommentResponse = CommentResponse.builder()
                    .comment(comment)
                    .build();
            commentResponseList.add(newCommentResponse);
        });

        return commentResponseList;
    }






}
