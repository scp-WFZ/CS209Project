package com.example.cs209project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Developer {
    @Id
    private Long id;
    private Long repo_id;
    private String name;

    public Developer(Long id, Long repo_id, String name) {
        this.id = id;
        this.repo_id = repo_id;
        this.name = name;
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
}
