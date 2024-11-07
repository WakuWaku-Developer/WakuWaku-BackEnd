package dev.backend.wakuwaku.domain.like.repository;

import dev.backend.wakuwaku.domain.Status;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LikesRepositoryTest {
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

    private static final double LAT = 99.99;

    private static final double LNG = 11.11;

    private static final String PHOTO_URL = "test-photo-url";

    private static final int USER_RATINGS_TOTAL = 999;

    private static final double RATING = 4.35;

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

        Location location = new Location(LAT, LNG);

        Photo photo = Photo.builder()
                           .photoUrl("https://lh3.googleusercontent.com/places/ANXAkqG2xQHKla3ebHNhRNrgMFi4WB6hGbR6LZTd2ig0PK5qTwkIvk0EP1fzPQ8UXmAt3FcU1Gz0XjYYCQJvFJQQVhAMss2GtKenoAI=s4800-w1440-h810")
                           .build();

        photos.add(photo);

        Places places = Places.builder()
                              .id(PLACE_ID)
                              .displayName(name)
                              .rating(RATING)
                              .userRatingCount(USER_RATINGS_TOTAL)
                              .location(location)
                              .photos(photos)
                              .build();

        saveRestaurant = restaurantRepository.save(new Restaurant(places));

        // 테스트용 찜 데이터를 저장
        Likes like = Likes.builder()
                          .member(testMember)
                          .restaurant(saveRestaurant)
                          .status(Status.ACTIVE)
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
        assertThat(foundLike.get().getStatus()).isEqualTo(Status.ACTIVE);
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

    @DisplayName("MemberId에 해당하는 찜 목록 전체 반환")
    @Test
    void findAllByMemberId() {
        // given
        Member member = memberRepository.save(createMember(1));

        Restaurant restaurant1 = restaurantRepository.save(createRestaurant(1));
        Restaurant restaurant2 = restaurantRepository.save(createRestaurant(2));
        Restaurant restaurant3 = restaurantRepository.save(createRestaurant(3));
        Restaurant restaurant4 = restaurantRepository.save(createRestaurant(4));

        Likes likes1 = likesRepository.save(createLikes(member, restaurant1));
        Likes likes2 = likesRepository.save(createLikes(member, restaurant2));
        Likes likes3 = likesRepository.save(createLikes(member, restaurant3));
        Likes likes4 = likesRepository.save(createLikes(member, restaurant4));

        Restaurant restaurant99 = restaurantRepository.save(createRestaurant(99));

        Likes likes99 = likesRepository.save(createLikes(member, restaurant99));
        likes99.updateLikeStatus(Status.INACTIVE);

        // when
        List<Likes> likes = likesRepository.findAllByMemberId(member.getId());

        // then
        assertThat(likes).isNotEmpty()
                               .hasSize(4)
                               .contains(likes1)
                               .contains(likes2)
                               .contains(likes3)
                               .contains(likes4)
                               .isNotIn(likes99);
    }

    @DisplayName("MemberId에 해당하는 찜 목록 중 요청 페이지만 반환")
    @Test
    void findAllByMemberIdAndPageable() {
        // given
        Member member = memberRepository.save(createMember(1));

        List<Likes> likesList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Restaurant restaurant = restaurantRepository.save(createRestaurant(i));

            Likes likes = likesRepository.save(createLikes(member, restaurant));
            likesList.add(likes);
        }

        Restaurant restaurant = restaurantRepository.save(createRestaurant(99));

        Likes likes = likesRepository.save(createLikes(member, restaurant));
        likes.updateLikeStatus(Status.INACTIVE);

        Pageable pageable = PageRequest.of(1, 10);

        // when
        Page<Likes> likesPage = likesRepository.findAllByMemberId(member.getId(), pageable);

        // then
        assertThat(likesPage.getTotalElements()).isEqualTo(20);

        assertThat(likesPage.getContent()).isNotEmpty()
                                                .hasSize(10)
                                                .contains(likesList.get(10))
                                                .contains(likesList.get(11))
                                                .contains(likesList.get(12))
                                                .contains(likesList.get(13))
                                                .contains(likesList.get(14))
                                                .contains(likesList.get(15))
                                                .contains(likesList.get(16))
                                                .contains(likesList.get(17))
                                                .contains(likesList.get(18))
                                                .contains(likesList.get(19))
                                                .isNotIn(likesList.get(0))
                                                .isNotIn(likesList.get(1))
                                                .isNotIn(likesList.get(2))
                                                .isNotIn(likesList.get(3))
                                                .isNotIn(likesList.get(4))
                                                .isNotIn(likesList.get(5))
                                                .isNotIn(likesList.get(6))
                                                .isNotIn(likesList.get(7))
                                                .isNotIn(likesList.get(8))
                                                .isNotIn(likesList.get(9))
                                                .isNotIn(likes);
    }

    private Member createMember(int number) {
        return Member.builder()
                     .oauthServerType(OauthServerType.GOOGLE)
                     .oauthServerId("test-server-id")
                     .email("test@test.com")
                     .role(Role.USER)
                     .birthday("test-birthday")
                     .nickname("test-nickname" + number)
                     .profileImageUrl("test-proflie-img-url")
                     .build();
    }

    private Restaurant createRestaurant(int number) {
        return Restaurant.builder()
                         .placeId(PLACE_ID + number)
                         .name(NAME)
                         .lat(LAT)
                         .lng(LNG)
                         .photo(PHOTO_URL)
                         .userRatingsTotal(USER_RATINGS_TOTAL)
                         .rating(RATING)
                         .build();
    }

    private Likes createLikes(Member member, Restaurant restaurant) {
        return Likes.builder()
                    .member(member)
                    .restaurant(restaurant)
                    .status(Status.ACTIVE)
                    .build();
    }
}
