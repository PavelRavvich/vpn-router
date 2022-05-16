package com.vpn.router.service;

import com.vpn.router.model.Route;
import com.vpn.router.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VpnConfigServiceImpl implements VpnConfigService {

    @Value("${vpn.routes.config}")
    private String routesConfigPath;

    @Value("${vpn.reconfigure.bash.cmd}")
    private String reconfigureCmd;

    private final HostRepository hostRepository;

    private final BashService bashService;

    @Override
    @SneakyThrows
    public void reconfigure() {
        List<String> lines = hostRepository
                .findAllByIsEnabled(true)
                .stream()
                .flatMap(host -> host.getRoutes().stream())
                .map(Route::getAddress)
                .collect(Collectors.toList());
        if (!lines.isEmpty()) {
            String ips = String.join("\n", lines);
            Files.write(Paths.get(routesConfigPath), ips.getBytes());
            bashService.executeBashCommand(reconfigureCmd);
        }
    }
}
