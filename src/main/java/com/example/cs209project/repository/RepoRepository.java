package com.example.cs209project.repository;

import com.example.cs209project.model.GitRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoRepository extends JpaRepository<GitRepository, Long> {
    @Query(
            value = "select * from git_repository where case when (?1<>'') then name=?1 else 1=1 end" , nativeQuery = true)
    List<GitRepository> getGithubReposInfos(String name);
}
