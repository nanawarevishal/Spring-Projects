package com.practiseservices.servicespractise.Services;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.ReelException;
import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Reel;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.ReelsRepository;
import com.practiseservices.servicespractise.Repository.UserRepository;

@Service
public class ReelServiceImpl implements ReelService{

    @Autowired
    private UserService userService;

    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Reel createReel(Reel reel, Long userId) {
        
        User user = userService.findUserById(userId);

        Reel createReel = new Reel();

        createReel.setCaption(reel.getCaption());
        createReel.setUser(user);
        createReel.setVideoUrl(reel.getVideoUrl());
        createReel.setCreatedAt(LocalDateTime.now());

        return reelsRepository.save(createReel);
    }

    @Override
    public List<Reel> findAllReels() {
        
        List<Reel>reels = reelsRepository.findAll();

        return reels;
    }

    @Override
    public List<Reel> findUsersReel(Long userId) {
        
        User user = userService.findUserById(userId);

        List<Reel>reels = reelsRepository.findByUserId(user.getId());

        return reels;
    }

    @Override
    public String deleteReel(Long reelId, Long userId) {
        
        User user = userService.findUserById(userId);

        Reel reel = findReelById(reelId);

        if(reel.getUser().getId() == user.getId()){
            reelsRepository.delete(reel);

            return "Reel deleted Successfully...!";
        }

        throw new ReelException("You cann't delete other Users Reels...!");
    }

    @Override
    public Reel saveReel(Long reelId, Long userId) {
        
        User user = userService.findUserById(userId);

        Reel reel = findReelById(reelId);

        if(user.getSavedReels().contains(reel)){
            user.getSavedReels().remove(reel);
        }
        else{
            user.getSavedReels().add(reel);
        }

        userRepository.save(user);

        return reel;
    }

    @Override
    public Reel likeReel(Long reelId, Long userId) {
        
        User user = userService.findUserById(userId);

        Reel reel = findReelById(reelId);

        if(user.getLikedReels().contains(reel)){
            user.getLikedReels().remove(reel);
        }

        else{
            user.getLikedReels().add(reel);
        }

        userRepository.save(user);

        return reel;
    }

    @Override
    public Reel findReelById(Long reelId) {
        
        Optional<Reel>opt = reelsRepository.findById(reelId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new ReelException("Reel not found with id: "+reelId);
    }

    @Override
    public List<Comment> findAllComments(Long reelId) {
       
        Reel reel = findReelById(reelId);

        List<Comment>reelComments = reel.getComments();

        return reelComments;
    }
    
}
