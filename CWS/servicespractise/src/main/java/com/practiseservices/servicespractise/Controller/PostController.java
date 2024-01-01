package com.practiseservices.servicespractise.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Post;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Response.ApiResponse;
import com.practiseservices.servicespractise.Services.PostService;
import com.practiseservices.servicespractise.Services.UserService;


@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    
    @PostMapping("/createPost")
    public ResponseEntity<Post> createPost(@RequestBody Post post,@RequestHeader("Authorization")String jwt){
        
        User reqUser = userService.findUserByJwt(jwt);

        Post createdPost = postService.createPost(post,reqUser.getId());

        return new ResponseEntity<Post>(createdPost,HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId")Long postId,@RequestHeader("Authorization")String jwt){

        User reqUser = userService.findUserByJwt(jwt);

        postService.deletePost(postId,reqUser.getId());

        ApiResponse response = new ApiResponse();
        response.setMsg("Post Deleted Successfully");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity <List<Post>> findPostByUserIdHandler(@RequestHeader("Authorization")String jwt){

        User reqUser = userService.findUserByJwt(jwt);

        List<Post>posts = postService.findPostByUserId(reqUser.getId());

        return new ResponseEntity<List<Post>>(posts, HttpStatus.FOUND);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity <Post> findPostByIdHandler(@PathVariable("postId")Long postId){

        Post posts = postService.findPostById(postId);

        return new ResponseEntity<Post>(posts, HttpStatus.FOUND);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPost(){

        return new ResponseEntity<List<Post>>(postService.findAllPost(), HttpStatus.FOUND);
    }
    
    @PutMapping("/savePost/{postId}")
    public ResponseEntity<Post> savePostHandler(@PathVariable("postId")Long postId,@RequestHeader("Authorization")String jwt){

        User reqUser = userService.findUserByJwt(jwt);

        Post post = postService.savePost(postId, reqUser.getId());

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/likePost/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable("postId")Long postId,@RequestHeader("Authorization")String jwt){

        User reqUser = userService.findUserByJwt(jwt);

        Post post = postService.likePost(postId, reqUser.getId());

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}
