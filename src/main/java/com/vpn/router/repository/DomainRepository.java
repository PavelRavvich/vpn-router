package com.vpn.router.repository;

import com.vpn.router.model.Domain;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Repository
@Transactional
public interface DomainRepository
        extends JpaRepository<Domain, Long>, JpaSpecificationExecutor<Domain> {

    @EntityGraph(attributePaths = {"routes"})
    void deleteByName(@NotNull String name);

    @Query("UPDATE Domain d SET d.isEnabled = :isEnabled, d.updatedAt = :updatedAt WHERE d.id = :id")
    void updateIsEnabled(Long id, Boolean isEnabled, Timestamp updatedAt);
}
