package com.example.cs209project.repository;

import com.example.cs209project.model.GitRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRepository extends JpaRepository<GitRepository, Long> {
}
