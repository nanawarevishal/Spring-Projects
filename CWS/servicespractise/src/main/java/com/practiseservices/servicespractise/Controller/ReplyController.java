package com.practiseservices.servicespractise.Controller;

import java.util.List;

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

import com.practiseservices.servicespractise.Model.Reply;
import com.practiseservices.servicespractise.Response.ApiResponse;
import com.practiseservices.servicespractise.Services.ReplyService;
import com.practiseservices.servicespractise.Services.UserService;


@RestController
@RequestMapping("/api/reply/")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserService userService;

    @PostMapping("create-reply/{commentId}")
    public ResponseEntity<Reply> createReply(@RequestBody Reply reply ,@PathVariable("commentId")Long commentId,@RequestHeader("Authorization")String jwt){

        Reply createReply = replyService.createReply(reply,userService.findUserByJwt(jwt).getId(),commentId) ;

        return new ResponseEntity<Reply>(createReply, HttpStatus.CREATED);
    }

    @GetMapping("replies/{commentId}")
    public ResponseEntity<List<Reply>> findAllReplyComment(@PathVariable("commentId")Long commentId){

        List<Reply>replies = replyService.findAllReplyofComment(commentId);

        return new ResponseEntity<List<Reply>>(replies, HttpStatus.FOUND);
    }

    @PutMapping("like-reply/{replyId}")
    public ResponseEntity<Reply> likeReply(@PathVariable("replyId")Long replyId,@RequestHeader("Authorization")String jwt){

        Reply reply = replyService.likeReply(replyId,userService.findUserByJwt(jwt).getId());

        return new ResponseEntity<Reply>(reply,HttpStatus.OK);
    }

    @DeleteMapping("delete-reply/{replyId}")
    public ResponseEntity<ApiResponse> deleteReplyHandler(@PathVariable("replyId")Long replyId,@RequestHeader("Authorization")String jwt){

        replyService.deleteReply(replyId, userService.findUserByJwt(jwt).getId());

        ApiResponse response = new ApiResponse();
        response.setMsg("Reply deleted Successfully...!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);

    }

}
