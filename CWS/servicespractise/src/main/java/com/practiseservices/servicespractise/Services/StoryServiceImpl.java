package com.practiseservices.servicespractise.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiseservices.servicespractise.Exception.StoryException;
import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Story;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Repository.StoryRepository;

@Service
public class StoryServiceImpl implements StoryService {
    
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Story createStory(Story story, Long userId) {
        
        User user = userService.findUserById(userId);

        Story newStory = new Story();
        newStory.setCaption(story.getCaption());
        newStory.setUser(user);
        newStory.setVideoUrl(story.getVideoUrl());
        newStory.setCreatedAt(LocalDateTime.now());
        newStory.setExpirationLocalTime(LocalDateTime.now().plusHours(24));

        return storyRepository.save(newStory);
    }

    @Override
    public List<Story> findAllStories() {
        List<Story>stories = storyRepository.findAll();
        return stories;
    }

    @Override
    public Story findAllStoryByUserId(Long userdId) {
        Story story= storyRepository.findByUserId(userdId);
        return story;
    }

    @Override
    public Story likeStory(Long storyId, Long userdId) {

        if(!isStoryExist(storyId)){
            throw new StoryException("Story doesn't exists with id: "+storyId);
        }
        
        User user = userService.findUserById(userdId);

        Story story = findStoryById(storyId);

        if(story.getLikedByUser().contains(user)){
            story.getLikedByUser().remove(user);
        }

        else{
            story.getLikedByUser().add(user);
        }

        return storyRepository.save(story);
    }

    @Override
    public Story saveStory(Long storyId, Long userdId) {

        if(!isStoryExist(storyId)){
            throw new StoryException("Story doesn't exists with id: "+storyId);
        }
        User user = userService.findUserById(userdId);

        Story story = findStoryById(storyId);

        if(story.getSavedByUser().contains(user)){
            story.getSavedByUser().remove(user);
        }

        else{
            story.getSavedByUser().add(user);
        }

        return storyRepository.save(story);
    }

    @Override
    public String deleteStory(Long storyId, Long userId) {

        if(!isStoryExist(storyId)){
            throw new StoryException("Story doesn't exists with id: "+storyId);
        }
        
        User user = userService.findUserById(userId);

        Story story = findStoryById(storyId);

        if(story.getUser().getId() == user.getId()){
            storyRepository.delete(story);
            return "Story deleted Successfully...!";
        }

        throw new StoryException("You cann't delete other User's story..!");
    }

    @Override
    public Story findStoryById(Long storyId) {

        if(!isStoryExist(storyId)){
            throw new StoryException("Story doesn't exists with id: "+storyId);
        }
        
        Optional<Story>opt = storyRepository.findById(storyId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new StoryException("Story not found with id: "+storyId);
        
    }

    @Override
    public List<Story> searchStoryByUserName(String query) {
        
        List<Story>stories = storyRepository.findByUser(query);

        return stories;
    }

    @Override
    public List<Comment> findAllComments(Long storyId) {

        if(!isStoryExist(storyId)){
            throw new StoryException("Story doesn't exists with id: "+storyId);
        }
        
        Story story = findStoryById(storyId);
        List<Comment>comments = story.getComments();

        return comments;
    }

    @Override
    public List<Story> findStoryFromFollowedUsers(Long userdId){

        User user = userService.findUserById(userdId);

        HashSet<Long>followedUser = user.getFollowers();

        List<Story>stories = new ArrayList<>();

        for(Long id : followedUser){

            Story story = findAllStoryByUserId(id);
            if(story!=null && story.getExpirationLocalTime().isAfter(LocalDateTime.now())){
                stories.add(story);
            }
        }

        return stories;
    }

    @Override
    public boolean isStoryExist(Long storyId) {
        
        Story story = findStoryById(storyId);

        if(story==null){
            throw new StoryException("Story not found with id: "+storyId);
        }

        if(LocalDateTime.now().isAfter(story.getExpirationLocalTime())){
            storyRepository.delete(story);
            return false;
        }

        return true;
    }
}
