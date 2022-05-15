package com.vpn.router.service;

import com.vpn.router.dto.HostDto;

import java.util.List;

public interface HostService {

    List<HostDto> list();

    void create(String url);

    void delete(String url);

    void updateRoutes(Long id);

    void disable(Long id);

    void enable(Long id);
}
