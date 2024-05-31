package dev.backend.wakuwaku.global.infra.google.places.old.details.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlaceDetailsStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class DetailsResponse {
    List<String> html_attributions;
    Result result;
    PlaceDetailsStatus status;
}
