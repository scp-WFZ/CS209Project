package com.example.cs209project.controller;

import com.example.cs209project.model.*;
import com.example.cs209project.repository.*;
import com.example.cs209project.service.DataService;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
public class RepoController {
    @Autowired
    private RepoRepository repoRepository;
    @Autowired
    private CommitRepository commitRepository;
    @Autowired
    private ReleaseRepository releaseRepository;
    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private IssueRepository issueRepository;


    @GetMapping("/allGitRepos")
    @CrossOrigin
    public List<GitRepository> getAllGitRepos() {
        return repoRepository.findAll();
    }

    @GetMapping("/gitRepo")
    @CrossOrigin
    public List<GitRepository> getGithubRepos(
            @RequestParam(value = "name", required = false)@Nullable String name) {
        return repoRepository.getGithubReposInfos(
                name != null ? name : "");
    }

    @GetMapping("/allIssueRepos")
    @CrossOrigin
    public List<Issue> getAllIssueRepos() {
        return issueRepository.findAll();
    }

    @GetMapping("/IssueRepo")
    @CrossOrigin
    public Map<String, Object> getIssueReposiroty(
            @RequestParam(value = "repos_name") String repos_name) {
        Map<String, Object> msi = DataService.analyseIssues(repos_name);
        if (!msi.containsKey("open")) {
            msi.put("open", 0);
        }
        if (!msi.containsKey("closed")) {
            msi.put("closed", 0);
        }
        return msi;
    }

    @GetMapping("/allCommitRepo")
    @CrossOrigin
    public List<Commit> getAllCommitRepos() {
        return commitRepository.findAll();
    }

    @GetMapping("/CommitRepo")
    @CrossOrigin
    public Map<String, List<Object>> getCommitReposiroty(
            @RequestParam(value = "repos_name") String repos_name,
            @RequestParam(value = "committer_id", required = false)@Nullable Integer committer_id,
            @RequestParam(value = "since", required = false)@Nullable String since,
            @RequestParam(value = "end", required = false)@Nullable String end,
            @RequestParam(value = "limit", required = false)@Nullable Integer limit) {
        List<Commit> lcr = commitRepository.getCommitRepository(
                repos_name,
                committer_id != null ? committer_id : -1,
                since != null ? since : "",
                end != null ? end : "",
                limit != null ? limit : 20);
        Map<String, Object> msi = DataService.analyseCommit(repos_name);
        Map<String, List<Object>> out = new HashMap<>();
        out.put("info", new LinkedList<>());
        for (Commit commit : lcr) {
            out.get("info").add(commit.getCreate_date());
            out.get("info").add(msi.get(String.valueOf(commit.getId())));
        }
        out.put("All_commit_nums", new LinkedList<>());
        out.get("All_commit_nums").add(0, msi.get("All_commit_nums"));
        return out;
    }

    @GetMapping("/allReleaseRepo")
    @CrossOrigin
    public List<Release> getAllReleaseRepos() {
        return releaseRepository.findAll();
    }

    @GetMapping("/ReleaseRepo")
    @CrossOrigin
    public Map<String, List<Object>> getReleaseReposiroty(
            @RequestParam(value = "repos_name") String repos_name,
            @RequestParam(value = "author_id", required = false)@Nullable Long author_id,
            @RequestParam(value = "name", required = false)@Nullable String name,
            @RequestParam(value = "since", required = false)@Nullable String since,
            @RequestParam(value = "limit", required = false)@Nullable Integer limit) {
        List<Release> rel = releaseRepository.getReleaseRepository(
                repos_name,
                author_id != null ? author_id : -1,
                name != null ? name : "",
                since != null ? since : "",
                limit != null ? limit : 20);
        Map<String, Object> msi = DataService.analyseRelease(repos_name);
        Map<String, List<Object>> out = new HashMap<>();
        out.put("info", new LinkedList<>());
        for (Release release : rel) {
            out.get("info").add(release.getCreate_date());
            out.get("info").add(msi.get(release.getName()) + " release " + release.getName());
        }
        out.put("All_repo_nums", new LinkedList<>());
        out.get("All_repo_nums").add(0, msi.get("All_repo_nums"));
        return out;
    }

    @GetMapping("/allDeveloperRepo")
    @CrossOrigin
    public List<Developer> getAllDeveloperRepos() {
        return developerRepository.findAll();
    }

    @GetMapping("/DeveloperRepo")
    @CrossOrigin
    public Map<String, List<Object>> getDeveloperReposiroty(
            @RequestParam(value = "repos_name") String repos_name) {
        List<Developer> drl = developerRepository.getDeveloperRepository(repos_name);
        Map<String, Object> msi = DataService.analyseDeveloper(repos_name);
        Map<String, List<Object>> out = new HashMap<>();
        out.put("info", new LinkedList<>());
        for (Developer developer : drl) {
            out.get("info").add(developer.getName());
            out.get("info").add(msi.getOrDefault(developer.getName(), 0));
        }
        return out;
    }

    @GetMapping("/Error")
    @CrossOrigin
    public String index() {
        return "Error";
    }

    @RequestMapping("/download/{filetype}/{filename}")
    @CrossOrigin
    public String downloadFiles(
            @PathVariable("filetype") String filetype,
            @PathVariable("filename") String filename,
            HttpServletResponse response) {
        String path =
                "C:\\Users\\amd yes\\Documents\\GitHub" +
                        "\\CS209Project\\src\\main\\resources\\static\\%s\\%s";
        File file = new File(String.format(path, filetype, filename));
        if (file.exists()) {
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLengthLong(file.length());
            response.setHeader("Content-Disposition", "attachment;fileName="
                            + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8));
            byte[] buffer = new byte[1024];
            FileInputStream fis;
            BufferedInputStream bis;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i;
                while ((i = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, i);
                    os.flush();
                }
                bis.close();
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "File";
    }
}
