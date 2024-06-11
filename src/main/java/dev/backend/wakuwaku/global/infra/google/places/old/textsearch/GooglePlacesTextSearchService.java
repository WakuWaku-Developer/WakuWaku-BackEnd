package dev.backend.wakuwaku.global.infra.google.places.old.textsearch;

import dev.backend.wakuwaku.global.infra.google.places.old.Result;
import dev.backend.wakuwaku.global.infra.google.places.old.photo.GooglePlacesPhotoService;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.TextSearchResponse;
import dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.response.dto.PlacePhoto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

import static dev.backend.wakuwaku.global.exception.WakuWakuException.INVALID_SEARCH_WORD;
import static dev.backend.wakuwaku.global.exception.WakuWakuException.NOT_EXISTED_NEXT_PAGE_TOKEN;
import static dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest.NEXT_PAGE_URL;
import static dev.backend.wakuwaku.global.infra.google.places.old.textsearch.dto.request.TextSearchRequest.TEXT_SEARCH_URL;

@Service
@RequiredArgsConstructor
public class GooglePlacesTextSearchService{
    private final GooglePlacesPhotoService photoService;

    private final RestClient restClient = RestClient.create();

    @Value("${google-places}")
    private String apiKey;

    public List<Result> textSearch(String searchWord) {
        if (searchWord == null || searchWord.isEmpty()) {
            throw INVALID_SEARCH_WORD;
        }

        TextSearchResponse responseByTextSearch = restClient.get()
                .uri(textSearchURI(searchWord))
                .retrieve()
                .body(TextSearchResponse.class);

        return resultsByTextSearch(responseByTextSearch);
    }

    // 나중에 사용 시 사용하는 메서드에서 이 메서드의 return 값이 null 인 경우를 처리하는 로직 구현
    public List<Result> textSearchByNextPageToken(String nextPageToken) {
        if (nextPageToken == null || nextPageToken.isEmpty()) {
            throw NOT_EXISTED_NEXT_PAGE_TOKEN;
        }

        TextSearchResponse responseByNextPageToken = restClient.get()
                .uri(nextPageTextSearchURI(nextPageToken))
                .retrieve()
                .body(TextSearchResponse.class);

        return resultsByTextSearch(responseByNextPageToken);
    }

    private List<Result> resultsByTextSearch(TextSearchResponse textSearchResponse) {
        // textSearchResponse 또는 result가 null인 경우 빈 리스트 반환
        if (textSearchResponse == null || textSearchResponse.getResults() == null || textSearchResponse.getResults().isEmpty()) {
            return Collections.emptyList();
        }

        List<Result> results = textSearchResponse.getResults();

        for (Result result : results) {
            List<PlacePhoto> photos = result.getPhotos();

            // 사진 리스트가 null이 아닌 경우에만 순회
            if (photos != null) {
                for (PlacePhoto photo : photos) {
                    if (photo == null || photo.getPhoto_reference() == null || photo.getPhoto_reference().isEmpty()) {
                        continue;
                    }

                    String photoUrl = getPhotoUrl(photo.getPhoto_reference());
                    photo.changeToUsablePhotoURL(photoUrl);
                }
            }
        }

        return results;
    }

    private String getPhotoUrl(String photoReference) {
        return photoService.getPhotosURL(photoReference, apiKey);
    }

    private String textSearchURI(String searchWord) {
        TextSearchRequest textSearchRequest = new TextSearchRequest(searchWord);

        String newTextQuery = textSearchRequest.getTextQuery();

        return TEXT_SEARCH_URL + newTextQuery + "&key=" + apiKey;
    }

    private String nextPageTextSearchURI(String nextPageToken) {
        return NEXT_PAGE_URL + nextPageToken + "&key=" + apiKey;
    }
}