package com.example.cs209project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class Release {
    @Id
    private Long id;
    private Long repo_id;
    private Long author_id;
    private String name;
    private Date create_date;
    private Date publish_date;

    public Release(Long id, Long repo_id, Long author_id, String name, Date create_date, Date publish_date) {
        this.id = id;
        this.repo_id = repo_id;
        this.author_id = author_id;
        this.name = name;
        this.create_date = create_date;
        this.publish_date = publish_date;
    }

    public Release() {
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

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }
}
