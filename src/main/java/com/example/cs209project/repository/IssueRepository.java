package com.example.cs209project.repository;

import com.example.cs209project.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query(
            value = "select ie.* from" +
                    " issue ie left join git_repository gri" +
                    " on gri.id = ie.repo_id" +
                    " where gri.name = ?1" +
                    " and case when (?2<>'') then substr(ie.open_date,1,10) >= ?2 else 1=1 end" +
                    " and case when (?3<>'') then substr(ie.close_date,1,10) <= ?3 else 1=1 end" +
                    " and case when (?4<>'') then ie.state = ?4 else 1=1 end" +
                    " and case when (?5<>'') then ie.title = ?5 else 1=1 end" +
                    " limit ?6", nativeQuery = true
    )
    List<Issue> getIssueEvents(
            String repos_name, String begin, String end, String state, String title, Integer limit);
}
