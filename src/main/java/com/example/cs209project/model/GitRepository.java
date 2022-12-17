package com.example.cs209project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class GitRepository {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int developerNum;
    private int most_active_developer;
    private int open_issues;
    private int close_issues;
    private double issue_solve_average;
    private int issue_solve_max_day;
    private int issue_solve_min_day;
    private int releases;
    private int commit_times;
    private int releases_top10_commits;
    private int releases_commits;

    public GitRepository() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDeveloperNum() {
        return developerNum;
    }

    public int getMost_active_developer() {
        return most_active_developer;
    }

    public int getOpen_issues() {
        return open_issues;
    }

    public int getClose_issues() {
        return close_issues;
    }

    public double getIssue_solve_average() {
        return issue_solve_average;
    }

    public int getIssue_solve_max_day() {
        return issue_solve_max_day;
    }

    public int getIssue_solve_min_day() {
        return issue_solve_min_day;
    }

    public int getReleases() {
        return releases;
    }

    public int getCommit_times() {
        return commit_times;
    }

    public int getReleases_top10_commits() {
        return releases_top10_commits;
    }

    public int getReleases_commits() {
        return releases_commits;
    }
}
