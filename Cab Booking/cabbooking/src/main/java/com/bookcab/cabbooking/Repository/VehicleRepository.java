package com.bookcab.cabbooking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookcab.cabbooking.Model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    
}
