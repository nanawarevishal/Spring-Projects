package com.practiseservices.servicespractise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Post;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Services.CommentService;
import com.practiseservices.servicespractise.Services.UserService;

@RestController
@RequestMapping("/api/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("createComment/{postId}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,@PathVariable("postId")Long postId,@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);

        Comment createCommet = commentService.createCommentPost(comment, postId, user.getId());

        return new ResponseEntity<Comment>(createCommet,HttpStatus.CREATED);
    }

    @PutMapping("likeComment/{commentId}")
    public ResponseEntity<Comment> likeComment(@PathVariable("commentId")Long commentId,@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);
        Comment comment = commentService.likeComment(commentId, user.getId());

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    @GetMapping("getComment/{commentId}")
    public ResponseEntity<Comment> findCommentByIdHandler(@PathVariable("commentId")Long commentId){

        Comment comment = commentService.findCommentById(commentId);

        return new ResponseEntity<Comment>(comment,HttpStatus.FOUND);
    }

    @GetMapping("user/{commentId}")
    public ResponseEntity<User> findUserByCommentIdHandler(@PathVariable("commentId")Long commentId){

        User user = commentService.findUserByCommentId(commentId);

        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }

    @GetMapping("post/{commentId}")
    public ResponseEntity<Post> findPostByCommentIdHandler(@PathVariable("commentId")Long commentId){

        Post post = commentService.findPostByCommentId(commentId);

        return new ResponseEntity<Post>(post, HttpStatus.FOUND);
    }


}
