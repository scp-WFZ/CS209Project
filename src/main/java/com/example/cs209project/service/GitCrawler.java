package com.example.cs209project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.io.*;
import org.jsoup.Jsoup;
import java.util.HashMap;
import java.util.Map;

public class GitCrawler {
    public static String token;
    public static Map<String, String> header;
    public GitCrawler() throws IOException {
        File file = new File("myToken.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String token;
        this.token = bufferedReader.readLine();
        bufferedReader.close();

        header = new HashMap<>();
        header.put("Authorization","token "+ this.token);
    }

    public static void getRepos() {
        String language = "Java";
        String url = String.format("https://api.github.com/search/repositories?q=language:%s&sort=stars&page=%d&per_page=%d",
                language, 1, 1);
        try {
            org.jsoup.Connection.Response res = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .headers(header)
                    .execute();
            JSONArray response = JSON.parseArray(res.body());
            FileWriter fileWriter = new FileWriter("src/main/resources/raw_data/developers2.json", true);
            fileWriter.write(response.toJSONString());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getIssues() {
        try {
            int page = 0;
            JSONArray data = new JSONArray();
            while (page < 8) {
                page += 1;
                String url = String.format("https://api.github.com/repos/%s/issues?state=all&page=%d&per_page=%d",
                        "TheAlgorithms/Java", page, 100);
                org.jsoup.Connection.Response res = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .headers(header)
                        .execute();
                data.add(JSON.parseArray(res.body()));
            }
            FileWriter fileWriter = new FileWriter("src/main/resources/raw_data/issues2.json", true);
            fileWriter.write(data.toJSONString());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDevelopers() {
        try {
            int page = 0;
            JSONArray data = new JSONArray();
            while (page < 5) {
                page += 1;
                String url = String.format("https://api.github.com/repos/%s/contributors?state=all&page=%d&per_page=%d",
                        "TheAlgorithms/Java", page,
                        100);
                org.jsoup.Connection.Response res = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .headers(header)
                        .execute();
                data.add(JSON.parseArray(res.body()));
            }
            FileWriter fileWriter = new FileWriter("src/main/resources/raw_data/developers2.json", true);
            fileWriter.write(data.toJSONString());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getCommits() {
        try {
            int page = 0;
            JSONArray data = new JSONArray();
            while (page < 18) {
                page += 1;
                String url = String.format("https://api.github.com/repos/%s/commits?state=all&page=%d&per_page=%d",
                        "TheAlgorithms/Java", page, 100);
                org.jsoup.Connection.Response res = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .headers(header)
                        .execute();
                data.add(JSON.parseArray(res.body()));
            }
            FileWriter fileWriter = new FileWriter("src/main/resources/raw_data/commits2.json", true);
            fileWriter.write(data.toJSONString());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getReleases() {
        try {
            int page = 0;
            JSONArray data = new JSONArray();
            while (page < 8) {
                page += 1;
                String url = String.format("https://api.github.com/repos/%s/releases?state=all&page=%d&per_page=%d",
                        "TheAlgorithms/Java", page, 100);
                org.jsoup.Connection.Response res = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .headers(header)
                        .execute();
                data.add(JSON.parseArray(res.body()));
            }
            FileWriter fileWriter = new FileWriter("src/main/resources/raw_data/releases2.json", true);
            fileWriter.write(data.toJSONString());
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        getRepos();
//        getIssues();
//        getDevelopers();
//        getCommits();
//        getReleases();
    }
}
