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

    public GitRepository(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GitRepository() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
