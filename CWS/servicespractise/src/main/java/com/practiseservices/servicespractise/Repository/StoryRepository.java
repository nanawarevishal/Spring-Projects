package com.practiseservices.servicespractise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practiseservices.servicespractise.Model.Story;

public interface StoryRepository extends JpaRepository<Story,Long> {
    
    @Query("select s from Story s where s.user.firstName Like %:query% OR s.user.lastName Like %:query% OR s.user.email Like %:query%")
    public List<Story> findByUser(@Param("query")String query);

    @Query("SELECT s FROM Story s WHERE s.user.id = :userId AND s.expirationLocalTime > CURRENT_TIMESTAMP")
    public Story findByUserId(@Param("userId") Long userId);

    // @Query("SELECT s FROM Story s " +
    // "JOIN s.user u " +
    // "JOIN u.followers f " +
    // "WHERE f.follower = :currentUserId " +
    // "AND s.expirationLocalTime > CURRENT_TIMESTAMP")
    // List<Story> findStoriesFromFollowedUsers(@Param("currentUserId") Long currentUserId);

    // // @Query("SELECT s FROM Story s " +
    // //        "JOIN s.likedByUser u " +
    // //        "WHERE u.id = :currentUserId " +
    // //        "AND s.expirationLocalTime > CURRENT_TIMESTAMP")
    // // List<Story> findStoriesFromFollowedUsers(@Param("currentUserId") Long currentUserId);
  


}
