package com.vpn.router.service;

import java.util.List;

public interface BashService {

    List<String> fetchRoutes(String hostname);
}
