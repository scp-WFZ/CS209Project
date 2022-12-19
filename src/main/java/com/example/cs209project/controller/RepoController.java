package com.example.cs209project.controller;

import com.example.cs209project.model.*;
import com.example.cs209project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/git-repository")
public class RepoController {
    @Autowired
    private RepoRepository repoRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private CommitRepository commitRepository;
    @Autowired
    private ReleaseRepository releaseRepository;
    @Autowired
    private DeveloperRepository developerRepository;

    @GetMapping("/allRepos")
    @CrossOrigin
    public List<GitRepository> getAllRepos(){
        return repoRepository.findAll();
    }

    @GetMapping("/repos/{repos_id}")
    @CrossOrigin
    public GitRepository getReposById(@PathVariable(value = "repos_id")Integer repos_id){
        return repoRepository.getById(Long.valueOf(repos_id));
    }

    @GetMapping("/filterRepos")
    @CrossOrigin
    public List<GitRepository> getGithubRepos(
            @RequestParam(value="name", required = false)@Nullable String name){
        return repoRepository.getGithubReposInfos(
                name!=null?name:"");
    }

    @GetMapping("/IssueRepo")
    @CrossOrigin
    public Map<String, List<Object>> getIssueReposiroty(
            @RequestParam(value="repos_name") String repos_name,
            @RequestParam(value="begin",required = false)@Nullable String begin,
            @RequestParam(value="end", required = false)@Nullable String end,
            @RequestParam(value="state", required = false)@Nullable String state,
            @RequestParam(value="title", required = false)@Nullable String title,
            @RequestParam(value="limit", required = false)@Nullable Integer limit){
        List<Issue> lie = issueRepository.getIssueEvents(
                repos_name,
                begin!=null?begin:"",
                end!=null?end:"",
                state!=null?state:"",
                title!=null?title:"",
                limit!=null?limit:20);
        return null;
    }

    @GetMapping("/CommitRepo")
    @CrossOrigin
    public Map<String, List<Object>> getCommitReposiroty(
            @RequestParam(value="repos_name") String repos_name,
            @RequestParam(value="committer_id",required = false)@Nullable Integer committer_id,
            @RequestParam(value="create_date", required = false)@Nullable String create_date,
            @RequestParam(value="limit", required = false)@Nullable Integer limit){
        List<Commit> lcr = commitRepository.getCommitRepository(
                repos_name,
                committer_id!=null?committer_id:-1,
                create_date!=null?create_date:"",
                limit!=null?limit:20);
        return null;
    }

    @GetMapping("/ReleaseRepo")
    @CrossOrigin
    public Map<String, List<Object>> getReleaseReposiroty(
            @RequestParam(value="repos_name") String repos_name,
            @RequestParam(value="author_id",required = false)@Nullable Long author_id,
            @RequestParam(value="name", required = false)@Nullable String name,
            @RequestParam(value="create_date", required = false)@Nullable String create_date,
            @RequestParam(value="publish_date", required = false)@Nullable String publish_date,
            @RequestParam(value="limit", required = false)@Nullable Integer limit){
        List<Release> lre = releaseRepository.getReleaseRepository(
                repos_name,
                author_id!=null?author_id:-1,
                name!=null?name:"",
                create_date!=null?create_date:"",
                publish_date!=null?publish_date:"",
                limit!=null?limit:20);
        return null;
    }

    @GetMapping("/DeveloperRepo")
    @CrossOrigin
    public Map<String, List<Object>> getReleaseReposiroty(
            @RequestParam(value="repos_name") String repos_name,
            @RequestParam(value="author_id",required = false)@Nullable String name){
        List<Developer> ldp = developerRepository.getDeveloperRepository(
                repos_name,
                name!=null?name:"");
        return null;
    }



}
