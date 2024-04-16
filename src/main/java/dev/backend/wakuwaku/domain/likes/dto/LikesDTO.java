package dev.backend.wakuwaku.domain.likes.dto;


import dev.backend.wakuwaku.domain.member.entity.MemberEntity;
import dev.backend.wakuwaku.domain.common.entity.StatusEntity;
import dev.backend.wakuwaku.domain.likes.entity.LikesEntity;
import dev.backend.wakuwaku.domain.restaurant.entity.RestaurantEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LikesDTO extends StatusEntity {
    private long id;

    private MemberEntity memberId;

    private RestaurantEntity restaurantId;
    private String likesStatus;


    public LikesEntity toLikesEntity(){
        return LikesEntity.builder()
                .memberId(memberId)
                .restaurantId(restaurantId)
                .likesStatus(likesStatus)
                .build();
    }
}

