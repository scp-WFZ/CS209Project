package com.example.cs209project.repository;

import com.example.cs209project.model.Issue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query(
            value = "select ie.* from"
                    + " issue ie left join git_repository gri"
                    + " on gri.id = ie.repo_id"
                    + " where gri.name = ?1", nativeQuery = true
    )
    List<Issue> getIssueEvents(String repos_name);
}
