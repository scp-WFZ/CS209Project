package com.example.cs209project.repository;

import com.example.cs209project.model.Developer;
import com.example.cs209project.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    @Query(
            value = "select dlp.* from" +
                    " developer dlp left join git_repository gri" +
                    " on gri.id = dlp.repo_id" +
                    " where gri.name = ?1" +
                    " and case when (?2<>'') then dlp.name =?2 else 1=1 end", nativeQuery = true)
    List<Developer> getDeveloperRepository(
            String repos_name, String name);
}
