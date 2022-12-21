package com.example.cs209project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageTransport {
    @GetMapping("/index")
    public String index(){
        return "Main";
    }
    @GetMapping()
    public String homepage(){
        return "redirect:/index";
    }
}