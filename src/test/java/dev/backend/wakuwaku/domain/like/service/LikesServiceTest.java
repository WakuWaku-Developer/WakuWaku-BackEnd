package dev.backend.wakuwaku.domain.like.service;

import dev.backend.wakuwaku.domain.likes.dto.LikesStatusType;
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
import dev.backend.wakuwaku.global.infra.google.places.dto.*;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.backend.wakuwaku.global.exception.ExceptionStatus.ALREADY_LIKED_EXCEPTION;
import static dev.backend.wakuwaku.global.exception.ExceptionStatus.LIKE_NOT_FOUND_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.Assertions.assertThat;

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

    private static final int TOTAL_PAGE = 5;

    private static final String MEMBER_EMAIL = "m@m.com";

    private final List<Photo> photos = new ArrayList<>();

    private final List<Review> reviews = new ArrayList<>();

    private Places places;

    private static final Long MEMBER_ID = 1L;

    private static final Long RESTAURANT_ID = 1L;

    private static final String NICKNAME = "MIN";

    @Test
    @DisplayName("찜 추가 성공 테스트")
    void addLikes_Success() {
        // given
        int number = 1;

        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(createMember(number)));
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(createRestaurant(number)));
        when(likesRepository.findByMemberIdAndRestaurantId(MEMBER_ID, RESTAURANT_ID)).thenReturn(Optional.empty());

        // when
        Likes like = likesService.addLikes(MEMBER_ID, RESTAURANT_ID);

        // then
        assertNotNull(like); // like가 null이 아닌지 확인합니다.
        assertEquals(MEMBER_EMAIL, like.getMember().getEmail()); // assertEquals를 사용하여 값 비교
        assertEquals(NICKNAME + number, like.getMember().getNickname());
        assertEquals(PLACE_ID, like.getRestaurant().getPlaceId());
        assertEquals(number, like.getRestaurant().getRating());

        assertEquals((LikesStatusType.Y), like.getLikesStatus());

    }

    @Test
    @DisplayName("이미 찜한 경우 예외 발생 테스트")
    void addLikes_AlreadyLikesd() {
        // given
        Likes existingLikes = Likes.builder()
                .member(createMember(1))
                .restaurant(createRestaurant(1))
                .likesStatus(LikesStatusType.Y)
                .build();
        when(likesRepository.findByMemberIdAndRestaurantId(MEMBER_ID, RESTAURANT_ID)).thenReturn(Optional.of(existingLikes));


        // when & then
        BDDAssertions.thenThrownBy(
                        () -> likesService.addLikes(MEMBER_ID, RESTAURANT_ID)
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
                .restaurant(createRestaurant(1))
                .likesStatus(LikesStatusType.Y)
                .build();

        when(likesRepository.findByMemberIdAndRestaurantId(MEMBER_ID, RESTAURANT_ID)).thenReturn(Optional.of(existingLikes));

        // when
        likesService.deleteLikes(MEMBER_ID, RESTAURANT_ID); // 반환값 검증이 아닌 메소드 호출 자체를 테스트

        // then
        assertEquals(LikesStatusType.N, existingLikes.getLikesStatus()); // 상태가 변경되었는지 검증
    }

    @Test
    @DisplayName("존재하지 않는 찜 삭제 시 예외 발생 테스트")
    void deleteLikes_NotFound() {
        // given
        when(likesRepository.findByMemberIdAndRestaurantId(MEMBER_ID, RESTAURANT_ID)).thenReturn(Optional.empty());

        // when & then

        // --
        //assertThrows(LIKE_NOT_FOUND_EXCEPTION.getClass(), () -> likeService.deleteLikes(memberId, restaurantId));
        BDDAssertions.thenThrownBy(
                () -> likesService.deleteLikes(MEMBER_ID, RESTAURANT_ID)
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

        Restaurant restaurant1 = createRestaurant(1);
        Restaurant restaurant2 = createRestaurant(2);
        Restaurant restaurant3 = createRestaurant(3);
        Restaurant restaurant4 = createRestaurant(4);
        Restaurant restaurant5 = createRestaurant(5);


        List<Likes> likesList = new ArrayList<>();
        likesList.add(createLikes(member, restaurant1));
        likesList.add(createLikes(member, restaurant2));
        likesList.add(createLikes(member, restaurant3));
        likesList.add(createLikes(member, restaurant4));
        likesList.add(createLikes(member, restaurant5));

        given(likesRepository.findByMemberId(MEMBER_ID)).willReturn(likesList);

        // when
        List<String> likedRestaurantPlaceIds = likesService.getLikedRestaurantPlaceIds(member);

        // then
        then(likesRepository).should().findByMemberId(MEMBER_ID);

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

        given(likesRepository.findByMemberId(MEMBER_ID)).willReturn(likesList);

        // when
        List<String> likedRestaurantPlaceIds = likesService.getLikedRestaurantPlaceIds(member);

        // then
        then(likesRepository).should().findByMemberId(MEMBER_ID);

        assertThat(likedRestaurantPlaceIds).isEmpty();
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

    private Restaurant createRestaurant(int number) {
        dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName name = new dev.backend.wakuwaku.global.infra.google.places.dto.DisplayName(NAME);

        Location location = new Location(35.686489, 139.697001);

        List<String> weekdayDescriptions = new ArrayList<>();
        weekdayDescriptions.add("월요일:오전11:00~오후10:00,화요일:오전11:00~오후10:00,수요일:오전11:00~오후10:00,목요일:오전11:00~오후10:00,금요일:오전11:00~오후10:00,토요일:오전11:00~오후10:00,일요일:오전11:00~오후10:00");

        CurrentOpeningHours currentOpeningHours = new CurrentOpeningHours(true, weekdayDescriptions);

        Photo photo = Photo.builder()
                .photoUrl("https://lh3.googleusercontent.com/places/ANXAkqG2xQHKla3ebHNhRNrgMFi4WB6hGbR6LZTd2ig0PK5qTwkIvk0EP1fzPQ8UXmAt3FcU1Gz0XjYYCQJvFJQQVhAMss2GtKenoAI=s4800-w1440-h810")
                .build();

        photos.add(photo);

        LocalizedText editorialSummary = new LocalizedText("다양한 반주와 함께 바삭한 튀김과 수제 우동을 맛 볼 수 있는 아담한 식당입니다.");

        LocalizedText reviewText = new LocalizedText("붓카게 우동과 자루소바 주문\\n직원 매우 친절함.의외로 튀김 맛있다\\n우동 자체는 꽤 맛있긴 하지만 이게 3시간을 기다릴 맛인가?이 가격 값을 하는가? 하면 별점 3점인 맛\\n웨이팅이 아예 없고 근처에 있다면 갈만한 집이긴 한데 절대 기다려서 먹을 맛 아님!!!!\\n면발이 탱글하다는데 개인적으로 오사카에서 먹은 다른 우동 집이 오백만배 탱글하고 맛있었음\\n손님도 로컬 거의 없고 대부분 외국인임\\n우연히 내가 그 근처를 지나가고 있는데 웨이팅이 없다? 할 때만 가십쇼");

        AuthorAttribution authorAttribution = new AuthorAttribution("촉촉한볼따구", "https://lh3.googleusercontent.com/a-/ALV-UjVoB_ILlpMn32pQwKbd5R99vuCX8mSb5T4rUzTRBBRts8CQolD9=s128-c0x00000000-cc-rp-mo");

        Review review = Review.builder()
                .relativePublishTimeDescription("3주 전")
                .rating(3)
                .text(reviewText)
                .authorAttribution(authorAttribution)
                .build();

        reviews.add(review);

        places = Places.builder()
                .id(PLACE_ID)
                .displayName(name)
                .rating(number)
                .location(location)
                .currentOpeningHours(currentOpeningHours)
                .photos(photos)
                .dineIn(true)
                .takeout(false)
                .delivery(false)
                .editorialSummary(editorialSummary)
                .reviews(reviews)
                .nationalPhoneNumber(null)
                .formattedAddress("일본 〒151-0053 Tokyo, Shibuya City, Yoyogi, 2-chōme−20−１６ 相馬ビル １F")
                .websiteUri("http://www.udonshin.com/")
                .userRatingCount(3965)
                .reservable(true)
                .servesBreakfast(false)
                .servesLunch(true)
                .servesDinner(true)
                .servesBeer(true)
                .servesWine(false)
                .servesVegetarianFood(false)
                .build();

        return new Restaurant(places);
    }

    private Likes createLikes(Member member, Restaurant restaurant) {
        return Likes.builder()
                .member(member)
                .restaurant(restaurant)
                .likesStatus(LikesStatusType.Y)
                .build();
    }
}
