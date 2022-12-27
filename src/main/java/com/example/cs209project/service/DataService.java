package com.example.cs209project.service;

import com.alibaba.fastjson.*;
import com.example.cs209project.model.*;
import com.example.cs209project.repository.*;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataService {
    private final RepoRepository repoRepository;
    private final DeveloperRepository developerRepository;
    private final CommitRepository commitRepository;
    private final ReleaseRepository releaseRepository;
    private final IssueRepository issueRepository;

    private final Map<String, Integer> Developermap = new HashMap<>();

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
        for (int i = 1; i <= 2; i++){
            addCommits(i);
            addDevelopers(i);
            addReleases(i);
            addIssues(i);
        }

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
        Date date;
        try {
            date = simpleDateFormat.parse(datestr);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return date;
    }

    public void addRepositories(){
        List<GitRepository> repositories = new ArrayList<>();
        repositories.add(new GitRepository(1L, "spring-boot"));
        repositories.add(new GitRepository(2L, "Python"));
        repoRepository.saveAll(repositories);
    }

    public void addCommits(int repoID){
        String jsonstring = getJSONString("src/main/resources/raw_data/commits" + repoID + ".json");

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
            commits.add(new Commit((long) repoID, committer_id, date));
        });
        commitRepository.saveAll(commits);
    }

    public void addDevelopers(int repoID){
        String jsonstring = getJSONString("src/main/resources/raw_data/developers" + repoID + ".json");

        JSONArray jsonArray = JSON.parseArray(jsonstring);
        List<Developer> developers = new ArrayList<>();
        jsonArray.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            Long id = jsonObject.getLong("id");
            String name =  jsonObject.getString("login");
            if(null != id && null != name) {
                developers.add(new Developer(id, (long) repoID, name));
            }
        });
        developerRepository.saveAll(developers);
    }

    public void addReleases(int repoID){
        String jsonstring = getJSONString("src/main/resources/raw_data/releases" + repoID + ".json");

        JSONArray jsonArray = JSON.parseArray(jsonstring);
        List<Release> releases = new ArrayList<>();
        jsonArray.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            JSONObject author = JSONObject.parseObject(jsonObject.getString("author"));
            Long id = jsonObject.getLong("id");
            Long repo_id = (long) repoID;
            Long author_id = author.getLong("id");
            String name = jsonObject.getString("name");
            Date create_date = string2Date(jsonObject.getString("created_at"));
            Date publish_date = string2Date(jsonObject.getString("published_at"));
            releases.add(new Release(id, repo_id, author_id, name, create_date, publish_date));
        });
        releaseRepository.saveAll(releases);
    }

    public void addIssues(int repoID){
        String jsonstring = getJSONString("src/main/resources/raw_data/issues" + repoID + ".json");

        JSONArray jsonArray = JSON.parseArray(jsonstring);
        List<Issue> issues = new ArrayList<>();
        jsonArray.forEach(e -> {
            JSONObject jsonObject = JSONObject.parseObject(e.toString());
            Long id = jsonObject.getLong("id");
            Long repo_id = (long) repoID;
            String title = jsonObject.getString("title");
            String state = jsonObject.getString("state");
            Date create_date = string2Date(jsonObject.getString("created_at"));
            Date closed_date = string2Date(jsonObject.getString("closed_at"));
            issues.add(new Issue(id, repo_id, title, state, create_date, closed_date));
        });
        issueRepository.saveAll(issues);
    }

    public static Map<String, Object> analyseIssues(@NotNull String repos_name){
        Map<String, Object> msi = new HashMap<>();
        String query = String.format("select i.state,count(i.state) " +
                "from issue i left join git_repository gri on gri.id = i.repo_id" +
                " where gri.name = '%s'" +
                " group by i.state;",repos_name);
        try{
            Connection conn;
            Statement stmt;
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs209",
                    "postgres","qscfthm123");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                msi.put(rs.getString(1),rs.getInt(2));
            }
            rs = stmt.executeQuery(String.format("""
                    select avg(date_part('day',cast(i.close_date as TIMESTAMP)
                    - cast(i.open_date as TIMESTAMP)))
                    from issue i left join git_repository gri on gri.id = i.repo_id where state = 'closed' and gri.name = '%s';""",repos_name));
            rs.next();
            msi.put("avg_closed_time",rs.getDouble(1));
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msi;
    }

    public static Map<String, Object> analyseRelease(@NotNull String repos_name){
        Map<String, Object> msi = new HashMap<>();
        String query = String.format("""
                select del.name, r.name
                from developer del join release r on del.id = r.author_id
                left join git_repository gr on del.repo_id = gr.id
                where gr.name = '%s'""",repos_name);
        try{
            Connection conn;
            Statement stmt;
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs209",
                    "postgres","qscfthm123");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                msi.put(rs.getString(2),rs.getString(1));
            }
            rs = stmt.executeQuery(String.format("""
                select count(rel.id)
                from release rel left join git_repository gr on rel.repo_id = gr.id
                where gr.name = '%s'""",repos_name));
            rs.next();
            msi.put("All_repo_nums", rs.getInt(1));
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msi;
    }

    public static Map<String, Object> analyseCommit(@NotNull String repos_name){
        Map<String, Object> msi = new HashMap<>();
        String query = String.format("""
                select del.name, c.id
                from developer del join commit c on del.id = c.committer_id
                left join git_repository gr on del.repo_id = gr.id
                where gr.name = '%s'""",repos_name);
        try{
            Connection conn;
            Statement stmt;
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs209",
                    "postgres","qscfthm123");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                msi.put(rs.getString(2),rs.getString(1));
            }
            rs = stmt.executeQuery(String.format("""
                    select count(c.id)
                    from commit c left join git_repository gr on c.repo_id = gr.id
                    where gr.name = '%s'""",repos_name));
            rs.next();
            msi.put("All_commit_nums", rs.getInt(1));
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msi;
    }

    public static Map<String, Object> analyseDeveloper(@NotNull String repos_name){
        Map<String, Object> msi = new HashMap<>();
        String query = String.format("""
                select d.id, d.name, count(*)
                from developer d
                join commit c on d.id = c.committer_id
                left join git_repository gr on d.repo_id = gr.id
                where gr.name = '%s'
                group by d.id""", repos_name);
        try{
            Connection conn;
            Statement stmt;
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs209",
                    "postgres","qscfthm123");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                msi.put(rs.getString(2),rs.getInt(3));
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msi;
    }

}
