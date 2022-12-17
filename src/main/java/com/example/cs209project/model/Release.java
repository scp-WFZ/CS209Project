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
    private Long author_id;
    private Long repo_id;
    private Date create_date;
    private Date publish_date;

    public Release() {
    }

    public Long getId() {
        return id;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public Long getRepo_id() {
        return repo_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public Date getPublish_date() {
        return publish_date;
    }
}
