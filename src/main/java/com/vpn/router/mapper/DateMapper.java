package com.vpn.router.mapper;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class DateMapper {
    public LocalDateTime asLocalDateTime(@NotNull Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
