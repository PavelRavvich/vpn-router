package com.vpnrouter.service;

import com.vpnrouter.model.Host;
import com.vpnrouter.model.Route;

import java.util.Collection;
import java.util.List;

public interface RouteService {

    void saveAll(Collection<Route> routes);

    void deleteAllByHost(Host host);

    List<Route> findAll();
}
