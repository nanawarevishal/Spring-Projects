package com.zosh.ecommersyoutube.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zosh.ecommersyoutube.Model.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {
    
    
}
