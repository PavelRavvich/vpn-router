package com.vpn.router.service;

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
import java.util.Collection;
import java.util.HashSet;
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
    public void createTmpAsDir() {
        Path path = Paths.get(tmpDir);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    @Override
    public Set<String> findIpsByDomainName(@NonNull String domainName) {
        String domain = domainName.replace("www.", "");
        long before = System.currentTimeMillis();
        Set<String> routes = getAddressesByDomain(domain)
                .stream()
                .map(this::getAutonomousSystem)
                .flatMap(Collection::stream)
                .map(as -> getRoute(domain, as))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        write(domain, routes);
        System.out.println((System.currentTimeMillis() - before) / 1000);
        return routes;
    }

    @SneakyThrows
    public void write(@NonNull String domain, @NonNull Set<String> routes) {
        String ips = String.join("\n", routes);
        Files.write(
                Paths.get(format(
                        "./%s_routes", domain.replace(".", "_"))
                ), ips.getBytes());
    }

    public Set<String> getAddressesByDomain(@NonNull String domain) {
        String pattern = format("%s has address", domain);
        return executeBashCommand(format(HOST_CMD, domain)).stream()
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
    public Set<String> getRoute(@NonNull String domain, @NonNull String as) {
        String tmpFile = format("%s/%s-%s.txt", tmpDir, domain, as);
        executeBashCommand(format(IPS_CMD, as, tmpFile));
        Path path = Paths.get(tmpFile);
        Set<String> ips = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.lines().forEach(item -> ips.add(item.replace("route:", "").trim()));
        }
        Files.delete(path);
        return ips;
    }

    @SneakyThrows
    public Set<String> executeBashCommand(@NonNull String command) {
        Set<String> result;
        String[] commands = {"bash", "-c", command};
        Process process = Runtime.getRuntime().exec(commands);
        process.waitFor();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            result = reader.lines()
                    .map(line-> format("%s\n", line))
                    .collect(Collectors.toSet());
        }
        if (result.isEmpty() && !command.contains(">>")) {
            throw new BashExecutionException();
        }
        return result;
    }
}
