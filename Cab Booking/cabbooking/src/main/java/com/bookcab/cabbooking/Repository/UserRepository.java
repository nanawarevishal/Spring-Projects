package com.bookcab.cabbooking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;


public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);

    @Query("SELECT R FROM Ride R WHERE R.status = 'COMPLETED' AND R.user.id = :userId")
    public List<Ride> getCompletedRides(@Param("userId") Long userId);
}
