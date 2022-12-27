package com.example.cs209project.repository;

import com.example.cs209project.model.Developer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    @Query(
            value = "select dlp.* from"
                    + " developer dlp left join git_repository gri"
                    + " on gri.id = dlp.repo_id"
                    + " where gri.name = ?1", nativeQuery = true)
    List<Developer> getDeveloperRepository(String repos_name);
}
