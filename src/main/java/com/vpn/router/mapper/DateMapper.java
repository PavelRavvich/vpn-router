package com.vpn.router.mapper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class DateMapper {
    public LocalDateTime asLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
