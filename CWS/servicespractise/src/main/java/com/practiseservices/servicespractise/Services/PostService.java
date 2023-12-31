package com.practiseservices.servicespractise.Services;

import java.util.List;

import com.practiseservices.servicespractise.Model.Post;

public interface PostService {

    public Post createPost(Post post,Long userId);

    public String deletePost(Long postId,Long userId);

    public List<Post> findPostByUserId(Long userId);

    public Post findPostById(Long postId);

    public List<Post> findAllPost();

    public Post savePost(Long postId,Long userId);

    public Post likePost(Long postId,Long userId);

}
