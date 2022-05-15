package com.vpn.router.service;

import com.vpn.router.dto.DomainRequest;
import com.vpn.router.dto.DomainResponse;
import com.vpn.router.dto.IdRequest;

import java.util.List;

public interface DomainService {

    List<DomainResponse> list();

    void create(DomainRequest request);

    void delete(DomainRequest request);

    void updateRoutes(IdRequest request);

    void disable(IdRequest request);

    void enable(IdRequest request);
}
