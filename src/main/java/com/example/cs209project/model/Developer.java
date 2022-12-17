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
    private String name;

    public Developer() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
