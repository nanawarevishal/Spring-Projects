package com.bookcab.cabbooking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.Ride;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    public Driver findByEmail(String email);

    @Query("SELECT R FROM Ride R WHERE R.status = 'REQUESTED' AND R.driver.id = :driverId")
    public List<Ride> getAllocatedRides(@Param("driverId") Long driverId);
    
    @Query("SELECT R FROM Ride R WHERE R.status = 'COMPLETED' AND R.driver.id = :driverId")
    public List<Ride> getCompletedRides(@Param("driverId") Long driverId);
    

}
