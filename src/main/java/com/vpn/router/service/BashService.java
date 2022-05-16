package com.vpn.router.service;

import java.util.List;
import java.util.Set;

public interface BashService {

    List<String> getRoutesByHost(String hostname);

    Set<String> executeBashCommand(String cmd);
}
