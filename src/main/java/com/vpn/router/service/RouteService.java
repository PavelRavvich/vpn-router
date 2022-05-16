package com.vpn.router.service;

import com.vpn.router.model.Host;
import com.vpn.router.model.Route;

import java.util.Collection;
import java.util.List;

public interface RouteService {

    void saveAll(Collection<Route> routes);

    void deleteAllByHost(Host host);

    List<Route> findAll();
}
