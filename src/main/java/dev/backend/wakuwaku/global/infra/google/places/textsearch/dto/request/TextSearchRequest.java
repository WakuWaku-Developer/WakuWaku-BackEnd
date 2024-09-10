package dev.backend.wakuwaku.global.infra.google.places.textsearch.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TextSearchRequest {
    public final String textQuery;

    public final String regionCode = "jp";

    public final String languageCode = "ko";

    public final String includedType = "restaurant";

    public final boolean strictTypeFiltering = true;

    public final double minRating = 4.0;
}
