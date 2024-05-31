package dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto;

import lombok.Getter;

@Getter
public class PlacePhoto {
    String photo_reference;

    public void changeToUsablePhotoURL(String realPhotoURL) {
        this.photo_reference = realPhotoURL;
    }
}
