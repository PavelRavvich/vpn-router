package com.vpnrouter.service;

import com.vpnrouter.dto.HostDto;

import java.util.List;

public interface HostService {

    List<HostDto> list();

    Long create(String url);

    void delete(String url);

    void updateRoutesByHostId(Long id);

    void disable(Long id);

    void enable(Long id);
}
