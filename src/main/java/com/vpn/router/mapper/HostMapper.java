package com.vpn.router.mapper;

import com.vpn.router.dto.HostDto;
import com.vpn.router.model.Host;
import com.vpn.router.model.Route;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface HostMapper {

    HostDto hostToHostDto(Host host);

    default List<Route> addressToRoute(@NotNull List<String> addresses, @NotNull Host host) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return addresses.stream()
                .map(address -> Route.builder()
                        .address(address)
                        .createdAt(now)
                        .host(host)
                        .build())
                .collect(Collectors.toList());
    }
}
