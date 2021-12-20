package com.example.demo.controller;

import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by Noumert on 11.08.2021.
 */
@Slf4j
@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/", "/main" })
    public String mainPage(Model model){
        List<GrantedAuthority> list = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(!Objects.equals(list.get(0).getAuthority(), "ROLE_ANONYMOUS")) {
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            try {
                model.addAttribute("uEmail", userService.findByEmail(userDetails.getUsername()).getEmailSecret());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            model.addAttribute("uEmail", "Guest");
        }

        return "index";
    }

}
