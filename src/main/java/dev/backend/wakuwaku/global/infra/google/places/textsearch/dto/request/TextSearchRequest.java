package dev.backend.wakuwaku.global.infra.google.places.textsearch.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TextSearchRequest {
    public final String textQuery;
    public final String languageCode;
}
