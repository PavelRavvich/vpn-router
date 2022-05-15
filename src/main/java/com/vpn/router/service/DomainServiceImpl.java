package com.vpn.router.service;

import com.vpn.router.dto.DomainRequest;
import com.vpn.router.dto.DomainResponse;
import com.vpn.router.dto.IdRequest;
import com.vpn.router.mapper.DomainMapper;
import com.vpn.router.model.Domain;
import com.vpn.router.model.Route;
import com.vpn.router.repository.DomainRepository;
import com.vpn.router.repository.RouteRepository;
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
public class DomainServiceImpl implements DomainService {

    private final DomainMapper domainMapper;

    private final DomainRepository domainRepository;

    private final RouteRepository routeRepository;

    private final BashService bashService;

    @Override
    public List<DomainResponse> list() {
        return domainRepository
                .findAll()
                .stream()
                .map(domainMapper::domainToDomainResponse)
                .sorted(Comparator.comparing(DomainResponse::getDomain))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(@NotNull DomainRequest request) {
        String name = request.getName().replace("www.", "");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Domain domain = domainRepository.save(
                Domain.builder()
                        .isEnabled(true)
                        .createdAt(now)
                        .name(name)
                        .build());
        List<Route> routes = bashService
                .fetchRoutes(name)
                .stream()
                .map(route -> Route.builder()
                        .createdAt(now)
                        .address(route)
                        .domain(domain)
                        .build()
                ).collect(Collectors.toList());
        routeRepository.saveAll(routes);
    }

    @Override
    public void updateRoutes(@NotNull IdRequest request) {
        Domain domain = domainRepository
                .findById(request.getId())
                .orElseThrow(NoSuchElementException::new);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Route> routes = bashService
                .fetchRoutes(domain.getName())
                .stream()
                .map(address -> Route.builder()
                        .address(address)
                        .createdAt(now)
                        .domain(domain)
                        .build()
                ).collect(Collectors.toList());
        routeRepository.deleteByDomain(domain);
        routeRepository.saveAll(routes);
    }

    @Override
    public void disable(@NotNull IdRequest request) {
        domainRepository.updateIsEnabled(request.getId(), false, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void enable(@NotNull IdRequest request) {
        domainRepository.updateIsEnabled(request.getId(), true, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void delete(@NotNull DomainRequest request) {
        domainRepository.deleteByName(request.getName());
    }
}
