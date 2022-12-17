package com.example.cs209project.controller;

import com.example.cs209project.repository.RepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git-repository")
public class RepoController {
    @Autowired
    private RepoRepository repoRepository;
}
