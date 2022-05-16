package com.vpn.router.service;

import com.vpn.router.model.Host;
import com.vpn.router.model.Route;
import com.vpn.router.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    public void saveAll(@NotNull Collection<Route> routes) {
        routeRepository.saveAll(routes);
    }

    @Override
    public void deleteAllByHost(@NotNull Host host) {
        routeRepository.deleteAllByHost(host);
    }

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }
}
