package com.example.cs209project.repository;

import com.example.cs209project.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
    @Query(
            value = "select rel.* from" +
                    " release rel left join git_repository gri" +
                    " on gri.id = rel.repo_id" +
                    " where gri.name = ?1" +
                    " and case when (?2<>-1) then rel.author_id =?2 else 1=1 end" +
                    " and case when (?3<>'') then rel.name =?3 else 1=1 end" +
                    " and case when (?4<>'') then substr(rel.create_date,1,10) =?4 else 1=1 end" +
                    " and case when (?5<>'') then substr(rel.publish_date,1,10) =?5 else 1=1 end" +
                    " limit ?6", nativeQuery = true)
    List<Release> getReleaseRepository(
            String repos_name, Long author_id, String name, String create_date, String publish_date, Integer limit);
}
