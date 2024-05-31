package dev.backend.wakuwaku.global.infra.google.places.old.photo.dto.response;

import dev.backend.wakuwaku.global.infra.google.places.old.photo.dto.response.dto.PhotoURL;
import lombok.Getter;

import java.util.List;

@Getter
public class PhotoResponse {
    List<PhotoURL> headers;
}
