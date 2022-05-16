package com.vpnrouter.repository;

import com.vpnrouter.model.Host;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface HostRepository
        extends JpaRepository<Host, Long>, JpaSpecificationExecutor<Host> {

    @Modifying
    @Query("UPDATE Host h SET h.isEnabled = :isEnabled, h.updatedAt = :updatedAt WHERE h.id = :id")
    void updateIsEnabled(@NotNull Long id, @NotNull Boolean isEnabled, @NotNull Timestamp updatedAt);

    Optional<Host> findTopByHostname(@NotNull String hostname);

    @EntityGraph(attributePaths = {"routes"})
    List<Host> findAllByIsEnabled(@NotNull Boolean isEnabled);
}
