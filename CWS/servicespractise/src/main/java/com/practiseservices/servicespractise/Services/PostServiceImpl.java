package com.practiseservices.servicespractise.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.PostException;
import com.practiseservices.servicespractise.Exception.UserException;
import com.practiseservices.servicespractise.Model.Post;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.PostRepository;
import com.practiseservices.servicespractise.Repository.UserRepository;


@Service    
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Post createPost(Post post, Long userId) {

        User user = userService.findUserById(userId);
        
        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setImageUrl(post.getImageUrl());
        newPost.setUser(user);
        newPost.setVideoUrl(post.getVideoUrl());

        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Long postId, Long userId) {

        User user = userService.findUserById(userId);

        Post post = findPostById(postId);

        if(post.getUser().getId() == user.getId()){

            postRepository.delete(post);
            return "Post deleted Successfully...!";
        }

        throw new PostException("You cann't delete other Users Post...!");

    }

    @Override
    public List<Post> findPostByUserId(Long userId) {

        User user = userService.findUserById(userId);

        if(user == null){
            throw new UserException("User not found with id: "+userId);
        }

        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Long postId) {

        Optional<Post>post = postRepository.findById(postId);

        if(!post.isPresent()){
            throw new PostException("Post not found with id: "+postId);
        }

        return post.get();
    }

    @Override
    public List<Post> findAllPost() {
        
        return postRepository.findAll();
    }

    @Override
    public Post savePost(Long postId, Long userId) {
        User user = userService.findUserById(userId);

        Post post = findPostById(postId);

        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);
        }
        else{
            user.getSavedPosts().add(post);
        }

        userRepository.save(user);

        return post;
    }

    @Override
    public Post likePost(Long postId, Long userId) {

        User user = userService.findUserById(userId);

        Post post = findPostById(postId);

        // if(post.getLikedByUsers().contains(user)){
        //     post.getLikedByUsers().remove(user);
        // }

        // else{
        //     post.getLikedByUsers().add(user);
        // }

        if(user.getLikedPosts().contains(post)){
            user.getLikedPosts().remove(post);
        }

        else{
            user.getLikedPosts().add(post);
        }

        userRepository.save(user);

        return post;
    }
    
}
