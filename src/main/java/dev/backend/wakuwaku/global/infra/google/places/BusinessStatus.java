package dev.backend.wakuwaku.global.infra.google.places;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BusinessStatus {
    OPERATIONAL("open"), CLOSED_TEMPORARILY("temporaryClosure"), CLOSED_PERMANENTLY("closedDown");

    private final String statusCode;
}
