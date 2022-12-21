package com.example.cs209project.repository;

import com.example.cs209project.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Long> {
    @Query(
            value = "select coi.* from" +
                    " commit coi left join git_repository gri" +
                    " on gri.id = coi.repo_id" +
                    " where gri.name = ?1" +
                    " and case when (?2<>-1) then coi.committer_id =?2 else 1=1 end" +
                    " and case when (?3<>'') then coi.create_date >= cast(?3 as TIMESTAMP) else 1=1 end" +
                    " and case when (?4<>'') then coi.create_date <= cast(?4 as TIMESTAMP) else 1=1 end" +
                    " limit ?5", nativeQuery = true)
    List<Commit> getCommitRepository(
            String repos_name, Integer committer_id, String since, String end, Integer limit);
}
