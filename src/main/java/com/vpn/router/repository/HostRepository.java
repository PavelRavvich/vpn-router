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
import java.util.Optional;

@Repository
@Transactional
public interface HostRepository
        extends JpaRepository<Host, Long>, JpaSpecificationExecutor<Host> {

    Optional<Host> findTopByHostname(@NotNull String hostname);

    @EntityGraph(attributePaths = {"routes"})
    void deleteById(@NotNull Long id);

    @Query("UPDATE Host h SET h.isEnabled = :isEnabled, h.updatedAt = :updatedAt WHERE h.id = :id")
    void updateIsEnabled(@NotNull Long id, @NotNull Boolean isEnabled, @NotNull Timestamp updatedAt);
}
