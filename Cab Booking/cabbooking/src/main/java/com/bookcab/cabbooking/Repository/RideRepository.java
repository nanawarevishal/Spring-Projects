package com.bookcab.cabbooking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookcab.cabbooking.Model.Ride;

public interface RideRepository extends JpaRepository<Ride,Long> {
    
}
