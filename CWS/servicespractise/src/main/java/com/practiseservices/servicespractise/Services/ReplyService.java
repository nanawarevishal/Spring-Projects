package com.practiseservices.servicespractise.Services;

import java.util.List;

import com.practiseservices.servicespractise.Model.Reply;

public interface ReplyService {
    
    public Reply createReply(Reply reply,Long userId,Long commentId);

    public List<Reply> findAllReplyofComment(Long commentId);

    public Reply likeReply(Long replyId,Long userId);

    public String deleteReply(Long replyId,Long userId);

    public Reply findReplyById(Long replyId);
}
