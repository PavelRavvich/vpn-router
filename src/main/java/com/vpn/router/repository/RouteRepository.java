package com.vpn.router.repository;

import com.vpn.router.model.Domain;
import com.vpn.router.model.Route;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RouteRepository extends JpaRepository<Route, Long>, JpaSpecificationExecutor<Route> {

    List<Route> findAllByDomain(@NotNull Domain domain);

    void deleteByDomain(@NotNull Domain domain);

}
