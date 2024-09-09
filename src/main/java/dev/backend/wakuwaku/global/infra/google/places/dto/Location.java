package dev.backend.wakuwaku.global.infra.google.places.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    double latitude;

    double longitude;
}
