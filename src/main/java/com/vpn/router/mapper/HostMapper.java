package com.vpn.router.mapper;

import com.vpn.router.dto.HostDto;
import com.vpn.router.model.Host;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface HostMapper {
    HostDto hostToHostDto(Host host);
}
