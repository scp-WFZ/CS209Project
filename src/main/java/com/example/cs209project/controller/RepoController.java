package com.example.cs209project.controller;

import com.example.cs209project.model.*;
import com.example.cs209project.repository.*;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

@RestController
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

    @GetMapping("/gitRepo")
    @CrossOrigin
    public List<GitRepository> getGithubRepos(
            @RequestParam(value="name", required = false)@Nullable String name){
        return repoRepository.getGithubReposInfos(
                name!=null?name:"");
    }

    @GetMapping("/IssueRepo")
    @CrossOrigin
    public Map<String, List<Object>> getIssueReposiroty(
            @RequestParam(value="repos_name") String repos_name){
        List<Issue> lie = issueRepository.getIssueEvents(
                repos_name);
        return null;
    }

    public static Map<String, Object> analyseIssues(@NotNull String repos_name){
        Map<String, Object> msi = new HashMap<>();
        String query = String.format("select i.state,count(i.state) from issue i left join github_repos_info gri on gri.id = i.repos_id" +
                " where gri.full_name = '%s'" +
                " group by i.state;",repos_name);
        try{
            Connection conn = null;
            Statement stmt = null;
            conn = DriverManager.getConnection(url,user,password);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                msi.put(rs.getString(1),rs.getInt(2));
            }
            rs = stmt.executeQuery(String.format("select i.labeled, count(i.*) from issue i left join github_repos_info gri on gri.id = i.repos_id where gri.full_name = '%s' group by i.labeled;",repos_name));
            while(rs.next()){
                msi.put(rs.getString(1),rs.getInt(2));
            }
            rs = stmt.executeQuery(String.format("select avg(date_part('day',cast(to_date(substr(i.closed_at,1,10), 'YYYY-MM-DD') as TIMESTAMP) - cast(to_date(substr(i.created_at,1,10), 'YYYY-MM-DD') as TIMESTAMP))) from issue i left join github_repos_info gri on gri.id = i.repos_id where state = 'closedI' and gri.full_name = '%s';",repos_name));
            rs.next();
            msi.put("avg_closed_time",rs.getDouble(1));
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msi;
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
    public List<Developer> getDeveloperReposiroty(
            @RequestParam(value="repos_name") String repos_name){
        return developerRepository.getDeveloperRepository(repos_name);
    }




}
