package com.example.cs209project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageTransport {
    @Deprecated
    @GetMapping("/repository")
    public String repository(){
        return "Repository";
    }
    @Deprecated
    @GetMapping("/Issue")
    public String issue(){
        return "Issue";
    }
    @Deprecated
    @GetMapping("/event")
    public String event(){
        return "Event";
    }
    @GetMapping("/index")
    public String index(){
        return "Main";
    }
    @GetMapping()
    public String homepage(){
        return "redirect:/index";
    }
}