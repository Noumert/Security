package com.example.demo.controller;

import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Noumert on 11.08.2021.
 */
@Slf4j
@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/", "/main" })
    public String mainPage(){
        return "index";
    }

}
