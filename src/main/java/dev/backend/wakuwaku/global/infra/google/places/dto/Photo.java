package dev.backend.wakuwaku.global.infra.google.places.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Photo {
    String name;

    int widthPx;

    int heightPx;

    List<AuthorAttribution> authorAttributions;

    String photoUrl;

    @Builder
    public Photo(String name, int widthPx, int heightPx, List<AuthorAttribution> authorAttributions, String photoUrl) {
        this.name = name;
        this.widthPx = widthPx;
        this.heightPx = heightPx;
        this.authorAttributions = authorAttributions;
        this.photoUrl = photoUrl;
    }

    public void createPhotoURL(String realPhotoURL) {
        this.photoUrl = realPhotoURL;
    }
}
