package com.vpn.router.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

}
