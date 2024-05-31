package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlaceDetailsStatus;
import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import lombok.Getter;

import java.util.List;

@Getter
public class TextSearchResponse {
    List<String> html_attributions;
    String next_page_token;
    List<Result> results;
    PlaceDetailsStatus status;
}
