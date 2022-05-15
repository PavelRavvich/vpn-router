package com.vpn.router.controller;

import com.vpn.router.dto.HostDto;
import com.vpn.router.service.HostService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @GetMapping("/list")
    public ResponseEntity<List<HostDto>> list() {
        return ResponseEntity.ok(hostService.list());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@NotNull @RequestBody @Valid Map<String, String> request) {
        hostService.create(request.get("hostname"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateRoutes(@NotNull @RequestBody Map<String, Long> request) {
        hostService.updateRoutes(request.get("id"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disable")
    public ResponseEntity<Void> disable(@NotNull @RequestBody Map<String, Long> request) {
        hostService.disable(request.get("id"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/enable")
    public ResponseEntity<Void> enable(@NotNull @RequestBody Map<String, Long> request) {
        hostService.enable(request.get("id"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@NotNull @RequestBody Map<String, String> request) {
        hostService.delete(request.get("url"));
        return ResponseEntity.ok().build();
    }
}