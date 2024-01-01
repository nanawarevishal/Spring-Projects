package com.practiseservices.servicespractise.Services;

import java.util.List;

import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Reel;


public interface ReelService {
    
    public Reel createReel(Reel reel,Long userId);

    public List<Reel> findAllReels();

    public List<Reel> findUsersReel(Long userId);

    public String deleteReel(Long reelId,Long userId);

    public Reel saveReel(Long reelId,Long userId);

    public Reel likeReel(Long reelId,Long userId);

    public Reel findReelById(Long reelId);

    public List<Comment> findAllComments(Long reelId);

}
