package com.practiseservices.servicespractise.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practiseservices.servicespractise.Model.Reel;

public interface ReelsRepository extends JpaRepository<Reel,Long> {
    
    @Query("SELECT r FROM Reel r WHERE r.user.id = :userId")
    public List<Reel> findByUserId(@Param("userId")Long userId);
}
