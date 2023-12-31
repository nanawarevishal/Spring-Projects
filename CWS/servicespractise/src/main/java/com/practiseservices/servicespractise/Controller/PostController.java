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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Post;
import com.practiseservices.servicespractise.Response.ApiResponse;
import com.practiseservices.servicespractise.Services.PostService;


@RestController
@RequestMapping("/postAPI")
public class PostController {

    @Autowired
    private PostService postService;

    
    @PostMapping("/createPost/user/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post,@PathVariable("userId")Long userId){
        
        Post createdPost = postService.createPost(post, userId);

        return new ResponseEntity<Post>(createdPost,HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePost/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId")Long postId,@PathVariable("userId")Long userId){

        postService.deletePost(postId, userId);

        ApiResponse response = new ApiResponse();
        response.setMsg("Post Deleted Successfully");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity <List<Post>> findPostByUserIdHandler(@PathVariable("userId")Long userId){

        List<Post>posts = postService.findPostByUserId(userId);

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
    
    @PutMapping("/savePost/{postId}/user/{userId}")
    public ResponseEntity<Post> savePostHandler(@PathVariable("postId")Long postId,@PathVariable("userId")Long userId){

        Post post = postService.savePost(postId, userId);

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/likePost/{postId}/user/{userId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable("postId")Long postId,@PathVariable("userId")Long userId){

        Post post = postService.likePost(postId, userId);

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}
