package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlacePhoto {
    String photo_reference;

    public void changeToUsablePhotoURL(String realPhotoURL) {
        this.photo_reference = realPhotoURL;
    }
}
