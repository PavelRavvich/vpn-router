package com.vpn.router.service;

import com.vpn.router.dto.HostDto;
import com.vpn.router.mapper.HostMapper;
import com.vpn.router.model.Host;
import com.vpn.router.repository.HostRepository;
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

    private final RouteService routeService;

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
                        .createdAt(now)
                        .isEnabled(true)
                        .hostname(hostname)
                        .build());
        routeService.saveAll(bashService.getRoutesByHost(host));
    }

    @Override
    @Transactional
    public void updateRoutesByHostId(@NotNull Long hostId) {
        Host host = hostRepository
                .findById(hostId)
                .orElseThrow(NoSuchElementException::new);
        routeService.deleteAllByHost(host);
        routeService.saveAll(bashService.getRoutesByHost(host));
        hostRepository.updateIsEnabled(hostId, true, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void disable(@NotNull Long id) {
        hostRepository.updateIsEnabled(id, false, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void enable(@NotNull Long id) {
        hostRepository.updateIsEnabled(id, true, new Timestamp(System.currentTimeMillis()));
    }


    @Override
    @SneakyThrows
    @Transactional
    public void delete(@NotNull String url) {
        URI uri = new URI(url);
        Host host = hostRepository
                .findTopByHostname(uri.getHost())
                .orElseThrow(NoSuchElementException::new);
        hostRepository.deleteById(host.getId());
    }
}
