package dev.backend.wakuwaku.global.infra.google.places.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentOpeningHours {
    boolean openNow;

    List<String> weekdayDescriptions;
}
