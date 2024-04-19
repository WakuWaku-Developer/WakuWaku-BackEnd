package dev.backend.wakuwaku.domain.likes.dto;


import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.common.entity.StatusEntity;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LikesDto extends StatusEntity {
    private long id;

    private Member memberId;

    private Restaurant restaurantId;
    private String likesStatus;


    public Likes toLikesEntity(){
        return Likes.builder()
                .memberId(memberId)
                .restaurantId(restaurantId)
                .likesStatus(likesStatus)
                .build();
    }
}

