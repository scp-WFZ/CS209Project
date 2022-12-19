package com.example.cs209project.service;

import com.alibaba.fastjson.*;
import com.example.cs209project.model.*;
import com.example.cs209project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataService {
    private final RepoRepository repoRepository;
    private final DeveloperRepository developerRepository;
    private final CommitRepository commitRepository;
    private final ReleaseRepository releaseRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public DataService(RepoRepository repoRepository, DeveloperRepository developerRepository, CommitRepository commitRepository,
                       ReleaseRepository releaseRepository, IssueRepository issueRepository) {
        this.repoRepository = repoRepository;
        this.developerRepository = developerRepository;
        this.commitRepository = commitRepository;
        this.releaseRepository = releaseRepository;
        this.issueRepository = issueRepository;
    }

    public void addEntries(){
        addRepositories();
        addDevelopers();
        addCommits();
        addReleases();
        addIssues();
    }

    public String getJSONString(String filePath) {
        String jsonstring = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            jsonstring = stringBuilder.toString();
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonstring;
    }

    public Date string2Date(String datestr){
        if(null == datestr) {
            return null;
        }
        datestr = datestr.replaceAll("Z","").replaceAll("T"," ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(datestr);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return date;
    }

    public void addRepositories(){
        GitRepository gitRepository = new GitRepository(1L, "spring-boot");
        repoRepository.saveAll(List.of(gitRepository));
    }

    public void addDevelopers(){
        String jsonstring = getJSONString("src/main/resources/raw_data/developers.json");

        JSONArray jsonArray = JSON.parseArray(jsonstring);
        List<Developer> developers = new ArrayList<>();
        jsonArray.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            Long id = jsonObject.getLong("id");
            String name =  jsonObject.getString("login");
            if(null != id && null != name) {
                developers.add(new Developer(id, 1L, name));
            }
        });
        developerRepository.saveAll(developers);
    }

    public void addCommits(){
        String jsonstring = getJSONString("src/main/resources/raw_data/commits.json");

        JSONArray jsonArray = JSON.parseArray(jsonstring);
        List<Commit> commits = new ArrayList<>();
        jsonArray.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            String commit =  jsonObject.getString("commit");
            String committer = JSONObject.parseObject(commit).getString("committer");
            String datestr = JSONObject.parseObject(committer).getString("date");
            Date date = string2Date(datestr);
            committer = jsonObject.getString("committer");
            Long committer_id =  JSONObject.parseObject(committer).getLong("id");
            commits.add(new Commit(1L, committer_id, date));
        });
        commitRepository.saveAll(commits);
    }

    public void addReleases(){
        String jsonstring = getJSONString("src/main/resources/raw_data/releases.json");

        JSONArray jsonArray = JSON.parseArray(jsonstring);
        List<Release> releases = new ArrayList<>();
        jsonArray.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            JSONObject author = JSONObject.parseObject(jsonObject.getString("author"));
            Long id = jsonObject.getLong("id");
            Long repo_id = 1L;
            Long author_id = author.getLong("id");
            String name = jsonObject.getString("name");
            Date create_date = string2Date(jsonObject.getString("created_at"));
            Date publish_date = string2Date(jsonObject.getString("published_at"));
            releases.add(new Release(id, repo_id, author_id, name, create_date, publish_date));
        });
        releaseRepository.saveAll(releases);
    }

    public void addIssues(){
        String jsonstring = getJSONString("src/main/resources/raw_data/all_issues.json");

        JSONArray jsonArray = JSON.parseArray(jsonstring);
        List<Issue> issues = new ArrayList<>();
        jsonArray.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            Long id = jsonObject.getLong("id");
            Long repo_id = 1L;
            String title = jsonObject.getString("title");
            String state = jsonObject.getString("state");
            Date create_date = string2Date(jsonObject.getString("created_at"));
            Date closed_date = string2Date(jsonObject.getString("closed_at"));
            issues.add(new Issue(id, repo_id, title, state, create_date, closed_date));
        });
        issueRepository.saveAll(issues);
    }

}
