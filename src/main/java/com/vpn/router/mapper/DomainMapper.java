package com.vpn.router.mapper;

import com.vpn.router.dto.DomainResponse;
import com.vpn.router.model.Domain;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {DateMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface DomainMapper {
    DomainResponse domainToDomainResponse(Domain domain);
}
