package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto;

import lombok.Getter;

@Getter
public enum PlaceDetailsStatus {
    OK("OK"), ZERO_RESULTS("ZERO_RESULTS"), INVALID_REQUEST("INVALID_REQUEST"), OVER_QUERY_LIMIT("OVER_QUERY_LIMIT"),
    REQUEST_DENIED("REQUEST_DENIED"), UNKNOWN_ERROR("UNKNOWN_ERROR");

    private final String statusCode;

    PlaceDetailsStatus(String statusCode) {
        this.statusCode = statusCode;
    }
}
