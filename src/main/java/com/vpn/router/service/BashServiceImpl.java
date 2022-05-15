package com.vpn.router.service;

import com.vpn.router.model.Host;
import com.vpn.router.model.Route;
import com.vpn.router.validation.BashExecutionException;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
public class BashServiceImpl implements BashService {

    @Value("${fs.as-tmp-dir}")
    private String tmpDir;

    private final static String HOST_CMD = "host %s";
    private final static String AUTONOMOUS_SYSTEM_CMD = "whois -h whois.radb.net %s";
    private final static String IPS_CMD = "whois -h whois.radb.net -- -i origin -T route %s | grep 'route:' >> %s";

    @SneakyThrows
    @PostConstruct
    public void createTmpAutonomousSystemDir() {
        Path path = Paths.get(tmpDir);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    @Override
    @SneakyThrows
    public List<Route> getRoutesByHost(@NonNull Host host) {
        Set<String> addresses = getAddressesByHost(host.getHostname());
        log.debug("Host: {}, has {} routes", host.getHostname(), addresses.size());
        updateVpnConfig(host.getHostname(), addresses);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return addresses.stream()
                .map(this::getAutonomousSystem)
                .flatMap(Collection::stream)
                .map(as -> getRoute(host.getHostname(), as))
                .flatMap(Collection::stream)
                .map(address -> Route.builder()
                        .address(address)
                        .createdAt(now)
                        .host(host)
                        .build()
                ).collect(Collectors.toList());
    }

    @SneakyThrows
    public void updateVpnConfig(@NonNull String host, @NonNull Set<String> routes) {
        String ips = String.join("\n", routes);
        Files.write(
                Paths.get(format(
                        "./%s_routes", host.replace(".", "_"))
                ), ips.getBytes());
    }

    public Set<String> getAddressesByHost(@NonNull String host) {
        String pattern = format("%s has address", host);
        return executeBashCommand(format(HOST_CMD, host)).stream()
                .filter(item -> !item.contains("IPv6"))
                .map(item -> item.replace(pattern, "").trim())
                .collect(Collectors.toSet());
    }

    public Set<String> getAutonomousSystem(@NonNull String ipAddress) {
        return executeBashCommand(format(AUTONOMOUS_SYSTEM_CMD, ipAddress))
                .stream()
                .filter(line -> line.contains("AS") && line.contains("origin"))
                .map(line -> line.replace("origin:", "").trim())
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    public Set<String> getRoute(@NonNull String host, @NonNull String as) {
        String tmpFile = format("%s/%s-%s.txt", tmpDir, host, as);
        executeBashCommand(format(IPS_CMD, as, tmpFile));
        Path path = Paths.get(tmpFile);
        Set<String> ips = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.lines().forEach(item ->
                    ips.add(item.replace("route:", "").trim()));
        }
        Files.delete(path);
        return ips;
    }

    @Override
    @SneakyThrows
    public Set<String> executeBashCommand(@NonNull String command) {
        Set<String> result;
        Process process = Runtime.getRuntime()
                .exec(new String[] {"bash", "-c", command});
        process.waitFor();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            result = reader.lines().collect(Collectors.toSet());
        }
        if (result.isEmpty() && !command.contains(">>")) {
            throw new BashExecutionException();
        }
        return result;
    }
}
