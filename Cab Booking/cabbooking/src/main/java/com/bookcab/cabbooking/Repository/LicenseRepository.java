package com.bookcab.cabbooking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookcab.cabbooking.Model.License;

public interface LicenseRepository extends JpaRepository<License,Long> {
    
}
