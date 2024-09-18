package dev.backend.wakuwaku.domain.like.service;

import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
import dev.backend.wakuwaku.domain.likes.dto.request.LikesRestaurantRequest;
import dev.backend.wakuwaku.domain.likes.dto.response.AllLikesResponse;
import dev.backend.wakuwaku.domain.likes.entity.Likes;
import dev.backend.wakuwaku.domain.likes.repository.LikesRepository;
import dev.backend.wakuwaku.domain.likes.service.LikesService;
import dev.backend.wakuwaku.domain.member.entity.Member;
import dev.backend.wakuwaku.domain.member.repository.MemberRepository;
import dev.backend.wakuwaku.domain.oauth.dto.OauthServerType;
import dev.backend.wakuwaku.domain.oauth.dto.Role;
import dev.backend.wakuwaku.domain.restaurant.entity.Restaurant;
import dev.backend.wakuwaku.domain.restaurant.repository.RestaurantRepository;
import dev.backend.wakuwaku.global.exception.WakuWakuException;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.backend.wakuwaku.global.exception.ExceptionStatus.ALREADY_LIKED_EXCEPTION;
import static dev.backend.wakuwaku.global.exception.ExceptionStatus.LIKE_NOT_FOUND_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LikesServiceTest {
    @InjectMocks
    private LikesService likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    private static final String PLACE_ID = "ChIJAQCl79GMGGARZheneHqgIUs";

    private static final String NAME = "우동신";

    private static final String MEMBER_EMAIL = "m@m.com";

    private static final double LAT = 99.99;

    private static final double LNG = 11.11;

    private static final String PHOTO_URL = "test-photo-url";

    private static final int USER_RATINGS_TOTAL = 999;

    private static final double RATING = 4.35;

    private static final Long MEMBER_ID = 1L;

    private static final Long RESTAURANT_ID = 1L;

    private static final Long LIKES_ID = 1L;

    private static final String NICKNAME = "MIN";

    @Test
    @DisplayName("찜 추가 성공 테스트")
    void addLikes_Success() {
        // given
        int number = 1;

        LikesRestaurantRequest likesRestaurantRequest = createLikesRestaurantRequest(number);

        Restaurant restaurant = createRestaurant();
        ReflectionTestUtils.setField(restaurant, "id", RESTAURANT_ID);

        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(createMember(number)));
        when(restaurantRepository.findByPlaceId(PLACE_ID + number)).thenReturn(Optional.of(restaurant));
        when(likesRepository.findByMemberIdAndRestaurantId(MEMBER_ID, RESTAURANT_ID)).thenReturn(Optional.empty());

        // when
        Likes like = likesService.addLikes(MEMBER_ID, likesRestaurantRequest);

        // then
        assertNotNull(like); // like가 null이 아닌지 확인합니다.
        assertEquals(MEMBER_EMAIL, like.getMember().getEmail()); // assertEquals를 사용하여 값 비교
        assertEquals(NICKNAME + number, like.getMember().getNickname());
        assertEquals(PLACE_ID, like.getRestaurant().getPlaceId());
        assertEquals(RATING, like.getRestaurant().getRating());

        assertEquals((LikesStatusType.Y), like.getLikesStatus());

    }

    @Test
    @DisplayName("이미 찜한 경우 예외 발생 테스트")
    void addLikes_AlreadyLikesd() {
        // given
        LikesRestaurantRequest likesRestaurantRequest = createLikesRestaurantRequest(1);

        Restaurant restaurant = createRestaurant();
        ReflectionTestUtils.setField(restaurant, "id", RESTAURANT_ID);

        Likes existingLikes = Likes.builder()
                                   .member(createMember(1))
                                   .restaurant(restaurant)
                                   .likesStatus(LikesStatusType.Y)
                                   .build();

        given(restaurantRepository.findByPlaceId(likesRestaurantRequest.getPlaceId())).willReturn(Optional.ofNullable(restaurant));
        given(likesRepository.findByMemberIdAndRestaurantId(MEMBER_ID, RESTAURANT_ID)).willReturn(Optional.of(existingLikes));

        // when & then
        BDDAssertions.thenThrownBy(
                        () -> likesService.addLikes(MEMBER_ID, likesRestaurantRequest)
                )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(ALREADY_LIKED_EXCEPTION);
    }

    @Test
    @DisplayName("찜 삭제 성공 테스트")
    void deleteLikes_Success() {
        // given
        Likes existingLikes = Likes.builder()
                                   .member(createMember(1))
                                   .restaurant(createRestaurant())
                                   .likesStatus(LikesStatusType.Y)
                                   .build();

        when(likesRepository.findById(LIKES_ID)).thenReturn(Optional.of(existingLikes));

        // when
        likesService.deleteLikes(LIKES_ID); // 반환값 검증이 아닌 메소드 호출 자체를 테스트

        // then
        assertEquals(LikesStatusType.N, existingLikes.getLikesStatus()); // 상태가 변경되었는지 검증
    }

    @Test
    @DisplayName("존재하지 않는 찜 삭제 시 예외 발생 테스트")
    void deleteLikes_NotFound() {
        // given
        when(likesRepository.findById(LIKES_ID)).thenReturn(Optional.empty());

        // when & then

        // --
        //assertThrows(LIKE_NOT_FOUND_EXCEPTION.getClass(), () -> likeService.deleteLikes(memberId, restaurantId));
        BDDAssertions.thenThrownBy(
                () -> likesService.deleteLikes(LIKES_ID)
        )
                .isInstanceOf(WakuWakuException.class)
                .extracting("status")
                .isEqualTo(LIKE_NOT_FOUND_EXCEPTION);
    }

    @DisplayName("멤버 Id로 해당 멤버의 찜 목록 PlaceId 반환")
    @Test
    void getLikedRestaurantPlaceIds() {
        // given
        Member member = createMember(1);
        member.createId(MEMBER_ID);

        Restaurant restaurant1 = createRestaurant();
        Restaurant restaurant2 = createRestaurant();
        Restaurant restaurant3 = createRestaurant();
        Restaurant restaurant4 = createRestaurant();
        Restaurant restaurant5 = createRestaurant();


        List<Likes> likesList = new ArrayList<>();
        likesList.add(createLikes(member, restaurant1));
        likesList.add(createLikes(member, restaurant2));
        likesList.add(createLikes(member, restaurant3));
        likesList.add(createLikes(member, restaurant4));
        likesList.add(createLikes(member, restaurant5));

        given(likesRepository.findAllByMemberId(MEMBER_ID)).willReturn(likesList);

        // when
        List<String> likedRestaurantPlaceIds = likesService.getLikedRestaurantPlaceIds(member);

        // then
        then(likesRepository).should().findAllByMemberId(MEMBER_ID);

        assertThat(likedRestaurantPlaceIds).hasSize(5);
        assertThat(likedRestaurantPlaceIds.get(0)).isEqualTo(PLACE_ID);
        assertThat(likedRestaurantPlaceIds.get(1)).isEqualTo(PLACE_ID);
        assertThat(likedRestaurantPlaceIds.get(2)).isEqualTo(PLACE_ID);
        assertThat(likedRestaurantPlaceIds.get(3)).isEqualTo(PLACE_ID);
        assertThat(likedRestaurantPlaceIds.get(4)).isEqualTo(PLACE_ID);
    }

    @DisplayName("멤버 Id로 해당 멤버의 찜 목록이 비어있을 때 빈 리스트를 반환")
    @Test
    void NoHaveLikedRestaurant() {
        // given
        Member member = createMember(1);
        member.createId(MEMBER_ID);

        List<Likes> likesList = new ArrayList<>();

        given(likesRepository.findAllByMemberId(MEMBER_ID)).willReturn(likesList);

        // when
        List<String> likedRestaurantPlaceIds = likesService.getLikedRestaurantPlaceIds(member);

        // then
        then(likesRepository).should().findAllByMemberId(MEMBER_ID);

        assertThat(likedRestaurantPlaceIds).isEmpty();
    }

    @DisplayName("페이징 처리된 회원의 찜 목록 조회")
    @Test
    void getPaginatedLikesList() {
        // given
        Member member = createMember(1);

        Restaurant restaurant1 = createRestaurant();
        Restaurant restaurant2 = createRestaurant();
        Restaurant restaurant3 = createRestaurant();
        Restaurant restaurant4 = createRestaurant();
        Restaurant restaurant5 = createRestaurant();

        Likes likes1 = createLikes(member, restaurant1);
        ReflectionTestUtils.setField(likes1, "id", 1L);

        Likes likes2 = createLikes(member, restaurant2);
        ReflectionTestUtils.setField(likes2, "id", 2L);

        Likes likes3 = createLikes(member, restaurant3);
        ReflectionTestUtils.setField(likes3, "id", 3L);

        Likes likes4 = createLikes(member, restaurant4);
        ReflectionTestUtils.setField(likes4, "id", 4L);

        Likes likes5 = createLikes(member, restaurant5);
        ReflectionTestUtils.setField(likes5, "id", 5L);

        List<Likes> likesList = List.of(likes1, likes2, likes3, likes4, likes5);

        // 0번 페이지 + 10개의 사이즈
        Pageable pageable = PageRequest.of(0, 10);

        Page<Likes> likesPage = new PageImpl<>(likesList, pageable, likesList.size());

        given(likesRepository.findAllByMemberId(MEMBER_ID, pageable)).willReturn(likesPage);

        // when
        AllLikesResponse paginatedLikesList = likesService.getPaginatedLikesList(MEMBER_ID, pageable);

        // then
        then(likesRepository).should().findAllByMemberId(MEMBER_ID, pageable);

        assertThat(paginatedLikesList).isNotNull();
        assertThat(paginatedLikesList.getLikesRestaurants()).hasSize(5);
        assertThat(paginatedLikesList.getTotalPages()).isEqualTo(1);
    }

    private Member createMember(int number) {
        return Member.builder()
                     .oauthServerId("testId")
                     .email(MEMBER_EMAIL)
                     .role(Role.USER)
                     .nickname(NICKNAME + number)
                     .oauthServerType(OauthServerType.NAVER)
                     .birthday("19990118")
                     .profileImageUrl("www.d.com")
                     .build();
    }

    private Restaurant createRestaurant() {
        return Restaurant.builder()
                         .placeId(PLACE_ID)
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
                    .likesStatus(LikesStatusType.Y)
                    .build();
    }

    private LikesRestaurantRequest createLikesRestaurantRequest(int number) {
        return LikesRestaurantRequest.builder()
                                     .placeId(PLACE_ID + number)
                                     .name(NAME)
                                     .lat(LAT)
                                     .lng(LNG)
                                     .photo(PHOTO_URL)
                                     .userRatingsTotal(USER_RATINGS_TOTAL)
                                     .rating(RATING)
                                     .build();
    }
}
