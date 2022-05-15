package com.vpn.router.service;

import com.vpn.router.dto.HostDto;
import com.vpn.router.mapper.HostMapper;
import com.vpn.router.model.Host;
import com.vpn.router.model.Route;
import com.vpn.router.repository.HostRepository;
import com.vpn.router.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {

    private final HostMapper hostMapper;

    private final HostRepository hostRepository;

    private final RouteRepository routeRepository;

    private final BashService bashService;

    @Override
    public List<HostDto> list() {
        return hostRepository
                .findAll()
                .stream()
                .map(hostMapper::hostToHostDto)
                .sorted(Comparator.comparing(HostDto::getHostname))
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    @Transactional
    public void create(@NotNull String url) {
        String hostname = new URI(url).getHost();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Host host = hostRepository.save(
                Host.builder()
                        .isEnabled(true)
                        .createdAt(now)
                        .hostname(hostname)
                        .build());
        List<Route> routes = bashService.fetchRoutes(hostname).stream()
                .map(route -> Route.builder()
                        .createdAt(now)
                        .address(route)
                        .host(host)
                        .build()
                ).collect(Collectors.toList());
        routeRepository.saveAll(routes);
    }

    @Override
    public void updateRoutes(@NotNull Long id) {
        Host host = hostRepository
                .findById(id)
                .orElseThrow(NoSuchElementException::new);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Route> routes = bashService
                .fetchRoutes(host.getHostname())
                .stream()
                .map(address -> Route.builder()
                        .address(address)
                        .createdAt(now)
                        .host(host)
                        .build()
                ).collect(Collectors.toList());
        routeRepository.deleteByHost(host);
        routeRepository.saveAll(routes);
    }

    @Override
    public void disable(@NotNull Long id) {
        hostRepository.updateIsEnabled(id, false, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void enable(@NotNull Long id) {
        hostRepository.updateIsEnabled(id, true, new Timestamp(System.currentTimeMillis()));
    }

    @SneakyThrows
    @Override
    public void delete(@NotNull String url) {
        hostRepository.deleteByHostname(new URI(url).getHost());
    }
}
