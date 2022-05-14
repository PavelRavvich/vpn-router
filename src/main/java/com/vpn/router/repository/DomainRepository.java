package com.vpn.router.repository;

import com.vpn.router.model.Domain;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author Pavel Ravvich.
 */
@Repository
@Transactional
public interface DomainRepository
        extends JpaRepository<Domain, Long>, JpaSpecificationExecutor<Domain> {

    @NotNull
    @Override
    @EntityGraph(attributePaths = {"hosts.ips"})
    Page<Domain> findAll(@Nullable Specification<Domain> spec, @NotNull Pageable pageable);
}
