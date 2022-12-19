package com.example.cs209project.repository;

import com.example.cs209project.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
}
