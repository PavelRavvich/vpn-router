package com.vpnrouter.service;

import com.vpnrouter.dto.HostDto;
import com.vpnrouter.mapper.HostMapper;
import com.vpnrouter.model.Host;
import com.vpnrouter.repository.HostRepository;
import com.vpnrouter.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    private final VpnConfigService vpnConfigService;

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
    public Long create(@NotNull String url) {
        String hostname = StringUtils.toHost(url);
        Host host = hostRepository.save(
                Host.builder()
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .hostname(hostname)
                        .isEnabled(true)
                        .build());
        List<String> routes = bashService.getRoutesByHost(hostname);
        routeService.saveAll(hostMapper.addressToRoute(routes, host));
        vpnConfigService.reconfigure();
        return host.getId();
    }

    @Override
    @Transactional
    public void updateRoutesByHostId(@NotNull Long hostId) {
        Host host = hostRepository
                .findById(hostId)
                .orElseThrow(NoSuchElementException::new);
        List<String> addresses = bashService
                .getRoutesByHost(host.getHostname());
        routeService.deleteAllByHost(host);
        routeService.saveAll(hostMapper.addressToRoute(addresses, host));
        hostRepository.updateIsEnabled(hostId, true,
                new Timestamp(System.currentTimeMillis()));
        vpnConfigService.reconfigure();
    }

    @Override
    public void disable(@NotNull Long id) {
        hostRepository.updateIsEnabled(id, false,
                new Timestamp(System.currentTimeMillis()));
        vpnConfigService.reconfigure();
    }

    @Override
    public void enable(@NotNull Long id) {
        hostRepository.updateIsEnabled(id, true,
                new Timestamp(System.currentTimeMillis()));
        vpnConfigService.reconfigure();
    }

    @Override
    @Transactional
    public void delete(@NotNull String url) {
        String hostname = StringUtils.toHost(url);
        Host host = hostRepository
                .findTopByHostname(hostname)
                .orElseThrow(NoSuchElementException::new);
        routeService.deleteAllByHost(host);
        hostRepository.delete(host);
        vpnConfigService.reconfigure();
    }
}
