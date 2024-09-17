package dev.backend.wakuwaku.domain.likes.dto.response;

import dev.backend.wakuwaku.domain.likes.entity.Likes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLikesResponse {
    private Long likesId;

    private String placeId;

    private String name;

    private double lat;

    private double lng;

    private String photo;

    private int userRatingsTotal;

    private double rating;

    public GetLikesResponse(Likes likes) {
        this.likesId = likes.getId();
        this.placeId = likes.getRestaurant().getPlaceId();
        this.name = likes.getRestaurant().getName();
        this.lat = likes.getRestaurant().getLat();
        this.lng = likes.getRestaurant().getLng();
        this.photo = likes.getRestaurant().getPhoto();
        this.userRatingsTotal = likes.getRestaurant().getUserRatingsTotal();
        this.rating = likes.getRestaurant().getRating();
    }
}
