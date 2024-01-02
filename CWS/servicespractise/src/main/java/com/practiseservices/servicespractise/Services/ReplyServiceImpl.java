package com.practiseservices.servicespractise.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.ReplyException;
import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Reply;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.ReplyRepository;


@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Override
    public Reply createReply(Reply reply, Long userId, Long commentId) {

        User user = userService.findUserById(userId);

        Comment comment = commentService.findCommentById(commentId);

        Reply createReply = new Reply();

        createReply.setComment(comment);
        createReply.setUser(user);
        createReply.setContent(reply.getContent());
        createReply.setCreatedAt(LocalDateTime.now());
        
        return replyRepository.save(createReply);
    }

    @Override
    public List<Reply> findAllReplyofComment(Long commentId) {
        
        Comment comment = commentService.findCommentById(commentId);

        List<Reply>replies = comment.getReplies();

        return replies;
    }

    @Override
    public Reply likeReply(Long replyId, Long userId) {
        
        Reply reply = findReplyById(replyId);

        User user = userService.findUserById(userId);

        if(reply.getLikedByUser().contains(user)){
            reply.getLikedByUser().remove(user);
        }

        else{
            reply.getLikedByUser().add(user);
        }

        Reply savedReply = replyRepository.save(reply);

        return savedReply;
    }

    @Override
    public Reply findReplyById(Long replyId) {
        
        Optional<Reply>opt = replyRepository.findById(replyId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new ReplyException("Reply doesn't exists with id: "+replyId);
    }

    @Override
    public String deleteReply(Long replyId, Long userId) {
        
        Reply reply = findReplyById(replyId);

        if(reply.getUser().getId() == userId){
            replyRepository.delete(reply);
            return "Reply deleted successfully";
        }

        throw new ReplyException("You cannt delete another user reply..!");
    }
    
}
