package com.practiseservices.servicespractise.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.CommentException;
import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Post;
import com.practiseservices.servicespractise.Model.Reel;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.CommentRepository;
import com.practiseservices.servicespractise.Repository.PostRepository;
import com.practiseservices.servicespractise.Repository.ReelsRepository;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    // @Autowired
    // @Lazy
    // private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReelService reelService;

    @Autowired
    private ReelsRepository reelsRepository;

    @Override
    public Comment createCommentPost(Comment comment, Long postId, Long userId) {
        User user = userService.findUserById(userId);

        Post post = postService.findPostById(postId);

        Comment createComment = new Comment();

        createComment.setContent(comment.getContent());
        createComment.setCreatedAt(LocalDateTime.now());
        createComment.setPost(post);
        createComment.setUser(user);

        Comment savedComment = commentRepository.save(createComment);

        post.getComments().add(savedComment);

        postRepository.save(post);
        
        return savedComment;
    }

    @Override
    public Comment likeComment(Long commentId, Long userId) {
        
        Comment comment = findCommentById(commentId);

        User user = userService.findUserById(userId);

        if(comment.getLiked().contains(user)){
            comment.getLiked().remove(user);
        }

        else{

            comment.getLiked().add(user);
        }

        commentRepository.save(comment);

        return comment;
    }

    @Override
    public Comment findCommentById(Long commentId) {
        
        Optional<Comment>opt = commentRepository.findById(commentId);

        if(opt.isPresent()){

            return commentRepository.findById(commentId).get();
        }

        throw new CommentException("Comment not found with comment id: "+commentId);
    }

    @Override
    public User findUserByCommentId(Long commentId) {
        
        Comment comment = findCommentById(commentId);

        User user = comment.getUser();

        return user;
    }

    @Override
    public Post findPostByCommentId(Long commentId) {
        
        Comment comment = findCommentById(commentId);

        Post post = comment.getPost();

        return post;
    }

    @Override
    public Comment createCommentReel(Comment comment, Long ReelId, Long userId) {
        
        User user = userService.findUserById(userId);

        Reel reel = reelService.findReelById(ReelId);

        Comment createComment = new Comment();
        createComment.setContent(comment.getContent());
        createComment.setReel(reel);
        createComment.setUser(user);
        createComment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(createComment);

        reel.getComments().add(savedComment);

        reelsRepository.save(reel);
        

        return savedComment;
    }

    @Override
    public Reel findReelByCommentId(Long commentId) {
        
        Comment comment = findCommentById(commentId);

        Reel reel = comment.getReel();

        return reel;
    }

    @Override
    public String deleteComment(Long commentId, Long userId) {
        
        Comment comment = findCommentById(commentId);

        if(comment.getUser().getId() == userId){
            commentRepository.delete(comment);
            return "Comment deleted Successfully...!";
        }

        throw new CommentException("You cannt delete other users comment...!");
    }
}
