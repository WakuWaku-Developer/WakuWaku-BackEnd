package dev.backend.wakuwaku.global.infra.google.places.textsearch.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.Places;
import lombok.Getter;

import java.util.List;

@Getter
public class TextSearchResponse {
    private List<Places> places;
    private String nextPageToken;
}
