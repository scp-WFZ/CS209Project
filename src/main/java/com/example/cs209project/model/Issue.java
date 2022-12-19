package com.example.cs209project.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Issue {
    @Id
    private Long id;
    private Long repo_id;
    @Column(length = 500)
    private String title;
    private String state;
    private Date open_date;
    private Date close_date;

    public Issue(Long id, Long repo_id, String title, String state, Date open_date, Date close_date) {
        this.id = id;
        this.repo_id = repo_id;
        this.title = title;
        this.state = state;
        this.open_date = open_date;
        this.close_date = close_date;
    }

    public Issue() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getOpen_date() {
        return open_date;
    }

    public void setOpen_date(Date open_date) {
        this.open_date = open_date;
    }

    public Date getClose_date() {
        return close_date;
    }

    public void setClose_date(Date close_date) {
        this.close_date = close_date;
    }
}
