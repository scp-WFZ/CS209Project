package com.example.cs209project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class Commit {
    @Id
    Long id;
    private String commiter;
    private String author;
    private Date date;

    public Commit() {
    }

    public Long getId() {
        return id;
    }

    public String getCommiter() {
        return commiter;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }
}
