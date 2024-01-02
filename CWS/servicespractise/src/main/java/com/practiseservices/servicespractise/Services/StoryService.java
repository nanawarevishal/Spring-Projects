package com.practiseservices.servicespractise.Services;

import java.util.List;

import com.practiseservices.servicespractise.Model.Comment;
import com.practiseservices.servicespractise.Model.Story;

public interface StoryService {
    

    public Story createStory(Story story,Long userId);

    public List<Story> findAllStories();

    public Story findAllStoryByUserId(Long userdId);

    public Story likeStory(Long storyId,Long userdId);

    public Story saveStory(Long storyId,Long userdId);

    public String deleteStory(Long storyId,Long userId);

    public Story findStoryById(Long storyId);

    public List<Story> searchStoryByUserName(String query);

    public List<Comment> findAllComments(Long storyId);

    public List<Story> findStoryFromFollowedUsers(Long userId);

    public boolean isStoryExist(Long storyId);
}
