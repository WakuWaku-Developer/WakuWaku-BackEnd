package dev.backend.wakuwaku.domain.like.repository;

import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.repository.LikesRepository;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import dev.backend.wakuwaku.global.infra.google.places.dto.Location;
import dev.backend.wakuwaku.global.infra.google.places.dto.Photo;
import dev.backend.wakuwaku.global.infra.google.places.dto.Places;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LikesRepositoryTest {
    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Member testMember;
    private Restaurant saveRestaurant;

    private static final String PLACE_ID = "ChIJAQCl79GMGGARZheneHqgIUs";
    private static final String NAME = "우동신";
    private final List<Photo> photos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // 테스트용 멤버를 생성하고 저장
        testMember = memberRepository.save(Member.builder()
                .oauthServerId("test_oauth_id")
                .oauthServerType(OauthServerType.GOOGLE)
                .email("test@example.com")
                .birthday("1990-01-01")
                .nickname("TestUser")
                .profileImageUrl("http://example.com/profile.jpg")
                .role(Role.USER)
                .build());

        // 테스트용 레스토랑을 생성하고 저장
        dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName name = new dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName(NAME);

        Location location = new Location(35.686489, 139.697001);

        Photo photo = Photo.builder()
                .photoUrl("https://lh3.googleusercontent.com/places/ANXAkqG2xQHKla3ebHNhRNrgMFi4WB6hGbR6LZTd2ig0PK5qTwkIvk0EP1fzPQ8UXmAt3FcU1Gz0XjYYCQJvFJQQVhAMss2GtKenoAI=s4800-w1440-h810")
                .build();

        photos.add(photo);

        Places places = Places.builder()
                .id(PLACE_ID)
                .displayName(name)
                .rating(4.1)
                .userRatingCount(3929)
                .location(location)
                .photos(photos)
                .build();

        saveRestaurant = restaurantRepository.save(new Restaurant(places));

        // 테스트용 찜 데이터를 저장
        Likes like = Likes.builder()
                .member(testMember)
                .restaurant(saveRestaurant)
                .likesStatus(LikesStatusType.Y)
                .build();
        likesRepository.save(like);
    }

    @DisplayName("회원정보로 해당하는 식당 찜 여부")
    @Test
    void FindByMemberIdAndRestaurantId_LikeFound() {
        // given
        Long memberId = testMember.getId();
        Long restaurantId = saveRestaurant.getId();

        // when
        Optional<Likes> foundLike = likesRepository.findByMemberIdAndRestaurantId(memberId, restaurantId);

        // then
        assertThat(foundLike).isPresent();
        assertThat(foundLike.get().getMember().getId()).isEqualTo(memberId);
        assertThat(foundLike.get().getRestaurant().getId()).isEqualTo(restaurantId);
        assertThat(foundLike.get().getLikesStatus()).isEqualTo(LikesStatusType.Y);
    }

    @DisplayName("존재하지 않는 회원과 식당 ID로 찜 조회 시 비어있는 결과 반환")
    @Test
    void FindByMemberIdAndRestaurantId_LikeNotFound() {
        // given
        Long nonExistentMemberId = 999L;
        Long nonExistentRestaurantId = 999L;

        // when
        Optional<Likes> foundLike = likesRepository.findByMemberIdAndRestaurantId(nonExistentMemberId, nonExistentRestaurantId);

        // then
        assertThat(foundLike).isNotPresent();
    }
}
