package com.practiseservices.servicespractise.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
    

    @RequestMapping("/")
    public String homeCString(){
        return "This is home Controller";
    }

  

    

}
