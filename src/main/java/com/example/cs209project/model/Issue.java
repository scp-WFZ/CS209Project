package com.example.cs209project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class Issue {
    @Id
    private Long id;
    private String title;
    private String state;
    private Date open_date;
    private Date close_date;
    private Long repo_id;
    private Long user_id;

    public Issue() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getState() {
        return state;
    }

    public Date getOpen_date() {
        return open_date;
    }

    public Date getClose_date() {
        return close_date;
    }

    public Long getRepo_id() {
        return repo_id;
    }

    public Long getUser_id() {
        return user_id;
    }
}
