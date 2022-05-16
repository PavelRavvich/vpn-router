package com.vpnrouter.mapper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class DateMapper {
    public LocalDateTime asLocalDateTime(Timestamp timestamp) {
        return Objects.nonNull(timestamp) ? timestamp.toLocalDateTime() : null;
    }
}
