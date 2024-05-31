package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOpeningHours {
    boolean open_now;
    List<String> weekday_text;
}
