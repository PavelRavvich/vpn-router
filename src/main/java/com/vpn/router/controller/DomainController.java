package com.vpn.router.controller;

import com.vpn.router.dto.DomainRequest;
import com.vpn.router.dto.DomainResponse;
import com.vpn.router.dto.IdRequest;
import com.vpn.router.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    @GetMapping("/domainList")
    public ResponseEntity<List<DomainResponse>> list() {
        return ResponseEntity.ok(domainService.list());
    }

    @PostMapping("/createDomain")
    public ResponseEntity<Void> create(@NotNull @RequestBody @Valid DomainRequest request) {
        domainService.create(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateRoutes")
    public ResponseEntity<Void> updateRoutes(@NotNull @RequestBody @Valid IdRequest request) {
        domainService.updateRoutes(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disableDomain")
    public ResponseEntity<Void> disable(@NotNull @RequestBody @Valid IdRequest request) {
        domainService.disable(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/enableDomain")
    public ResponseEntity<Void> enable(@NotNull @RequestBody @Valid IdRequest request) {
        domainService.enable(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteDomain")
    public ResponseEntity<Void> delete(@NotNull @RequestBody @Valid DomainRequest request) {
        domainService.delete(request);
        return ResponseEntity.ok().build();
    }
}
