package com.vpnrouter.repository;

import com.vpnrouter.model.Host;
import com.vpnrouter.model.Route;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RouteRepository extends JpaRepository<Route, Long>, JpaSpecificationExecutor<Route> {

    void deleteAllByHost(@NotNull Host host);
}
