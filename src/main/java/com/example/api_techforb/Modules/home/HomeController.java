package com.example.api_techforb.Modules.home;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/home")
public class HomeController {


    @PostMapping(value = "dashboard")
    public String welcome() {
        return "Welcome from secure endpoint";
    }
}
