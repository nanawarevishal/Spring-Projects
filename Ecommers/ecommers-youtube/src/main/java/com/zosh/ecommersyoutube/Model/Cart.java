package com.zosh.ecommersyoutube.Model;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cart {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long Id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem>cartItems = new HashSet<>();

    private double totalPrice;

    private int totalItem;

    private int totalDiscountedPrice;

    private int discount;
    
}
