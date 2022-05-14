package com.vpn.router.controller;

import com.vpn.router.service.BashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/vpn")
@RequiredArgsConstructor
public class VpnController {

    private final BashService bashService;

    @GetMapping
    public ResponseEntity<Set<String>> updateRoute(
            @RequestParam(name = "domain") String domain,
            @RequestParam(name = "isEnabled") Boolean isEnabled) {
        return ResponseEntity.ok(bashService.findIpsByDomainName(domain));
    }
}
