package com.vpn.router.repository;

import com.vpn.router.model.Host;
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
public interface HostRepository
        extends JpaRepository<Host, Long>, JpaSpecificationExecutor<Host> {

    @EntityGraph(attributePaths = {"routes"})
    void deleteByHostname(@NotNull String name);

    @Query("UPDATE Host h SET h.isEnabled = :isEnabled, h.updatedAt = :updatedAt WHERE h.id = :id")
    void updateIsEnabled(Long id, Boolean isEnabled, Timestamp updatedAt);
}
