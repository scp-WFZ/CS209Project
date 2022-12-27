package com.example.cs209project.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Commit {
    @Id
    @GeneratedValue
    private Long id;
    private Long repo_id;
    private Long committer_id;
    private Date create_date;

    public Commit(Long repo_id, Long committer_id, Date create_date) {
        this.repo_id = repo_id;
        this.committer_id = committer_id;
        this.create_date = create_date;
    }

    public Commit() {
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

    public Long getCommitter_id() {
        return committer_id;
    }

    public void setCommitter_id(Long committer_id) {
        this.committer_id = committer_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
