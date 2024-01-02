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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Story;
import com.practiseservices.servicespractise.Response.ApiResponse;
import com.practiseservices.servicespractise.Services.StoryService;
import com.practiseservices.servicespractise.Services.UserService;

@RestController
@RequestMapping("/api/story/")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("create-story")
    public ResponseEntity<Story> createStory(@RequestBody Story story,@RequestHeader("Authorization")String jwt){

        Story newStory = storyService.createStory(story,userService.findUserByJwt(jwt).getId());

        return new ResponseEntity<Story>(newStory,HttpStatus.CREATED);
    }


    @PutMapping("like-story/{storyId}")
    public ResponseEntity<Story> likeStoryHandler(@PathVariable("storyId")Long storyId,@RequestHeader("Authorization")String jwt){
        
        Story story = storyService.likeStory(storyId,userService.findUserByJwt(jwt).getId());

        return new ResponseEntity<Story>(story,HttpStatus.OK);
    }

    @PutMapping("save-story/{storyId}")
    public ResponseEntity<Story> saveStoryHandler(@PathVariable("storyId")Long storyId,@RequestHeader("Authorization")String jwt){
        
        Story story = storyService.saveStory(storyId,userService.findUserByJwt(jwt).getId());

        return new ResponseEntity<Story>(story,HttpStatus.OK);
    }

    @DeleteMapping("delete-story/{storyId}")
    public ResponseEntity<ApiResponse> deleteStoryHandler(@PathVariable("storyId")Long storyId,@RequestHeader("Authorization")String jwt){

        storyService.deleteStory(storyId,userService.findUserByJwt(jwt).getId());

        ApiResponse response = new ApiResponse();
        response.setMsg("Story deleted Successfully..!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("search-story")
    public ResponseEntity<List<Story>> searchStoryByName(@RequestParam("query")String query){

        List<Story> stories = storyService.searchStoryByUserName(query);

        return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
    }

    @GetMapping("followed-user-stories")
    public ResponseEntity<List<Story>> findStoryFromFollowedUsersHandler(@RequestHeader("Authorization")String jwt){

        List<Story>stories = storyService.findStoryFromFollowedUsers(userService.findUserByJwt(jwt).getId());

        return new ResponseEntity<List<Story>>(stories, HttpStatus.OK);
    }

    @GetMapping("all-stories")
    public ResponseEntity<List<Story>> getAllStories(){

        List<Story>stories = storyService.findAllStories();
        return new ResponseEntity<List<Story>>(stories,HttpStatus.FOUND);        
    }

    
}
