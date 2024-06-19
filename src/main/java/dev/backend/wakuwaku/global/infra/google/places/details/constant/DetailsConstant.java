package dev.backend.wakuwaku.global.infra.google.places.details.constant;

public class DetailsConstant {
    public static final String DETAILS_URL = "https://places.googleapis.com/v1/places/";
    public static final String DETAILS_RESPONSE_FIELDS =
            "?fields=displayName.text,rating,photos.name,photos.widthPx,photos.heightPx,photos.authorAttributions.displayName,photos.authorAttributions.photoUri,userRatingCount,currentOpeningHours.openNow,currentOpeningHours.weekdayDescriptions,editorialSummary.text" +
                    ",reviews.relativePublishTimeDescription,reviews.rating,reviews.text.text,reviews.authorAttribution.displayName,reviews.authorAttribution.photoUri" +
                    ",nationalPhoneNumber,formattedAddress,websiteUri,dineIn,takeout,delivery,reservable,servesBreakfast,servesLunch,servesDinner,servesBeer,servesWine,servesVegetarianFood,businessStatus";
    public static final String DETAILS_LANGUAGE_CODE = "&languageCode=ko";
}
