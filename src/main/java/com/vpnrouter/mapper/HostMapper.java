package com.vpnrouter.mapper;

import com.vpnrouter.dto.HostDto;
import com.vpnrouter.model.Host;
import com.vpnrouter.model.Route;
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
