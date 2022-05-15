package com.vpn.router.service;

import com.vpn.router.model.Host;
import com.vpn.router.model.Route;

import java.util.List;
import java.util.Set;

public interface BashService {

    List<Route> getRoutesByHost(Host host);

    Set<String> executeBashCommand(String cmd);
}
