package com.practiseservices.servicespractise.Services;

import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Post;
import com.practiseservices.servicespractise.Model.Reel;
import com.practiseservices.servicespractise.Model.User;

public interface CommentService {
    
    public Comment createCommentPost(Comment comment,Long postId,Long userId);

    public Comment createCommentReel(Comment comment,Long ReelId,Long userId);

    public Comment likeComment(Long commentId,Long userId);

    public Comment findCommentById(Long commentId);

    public User findUserByCommentId(Long commentId);

    public Post findPostByCommentId(Long commentId);

    public Reel findReelByCommentId(Long commentId);

}
