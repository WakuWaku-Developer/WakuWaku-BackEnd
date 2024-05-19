package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PlaceOpeningHours {
    boolean open_now;
    List<String> weekday_text;
}
