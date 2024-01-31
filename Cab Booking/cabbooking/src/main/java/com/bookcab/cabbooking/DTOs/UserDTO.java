package com.bookcab.cabbooking.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String mobile;
}
