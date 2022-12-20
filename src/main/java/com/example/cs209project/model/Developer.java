package com.example.cs209project.model;

import javax.persistence.*;

@Entity
@Table
public class Developer {
    @Id
    private Long id;
    private Long repo_id;
    private String name;

    private Integer commit_times;

    public Developer(Long id, Long repo_id, String name, Integer commit_times) {
        this.id = id;
        this.repo_id = repo_id;
        this.name = name;
        this.commit_times = commit_times;
    }

    public Developer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepo_id() {
        return repo_id;
    }

    public void setRepo_id(Long repo_id) {
        this.repo_id = repo_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCommit_times() {
        return commit_times;
    }

    public void setCommit_times(Integer commit_times) {
        this.commit_times = commit_times;
    }
}
