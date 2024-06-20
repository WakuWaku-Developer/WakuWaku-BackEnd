package dev.backend.wakuwaku.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {
    // common exception
    INVALID_PARAMETER(BAD_REQUEST, 2000, "잘못된 요청이 존재합니다."),
    INVALID_URL(BAD_REQUEST, 2001, "잘못된 URL 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 2002,"서버 내부 오류입니다."),
    NOT_EXISTED_FILE(NOT_EXTENDED, 2003,"존재하지 않는 파일입니다."),

    // user exception
    DUPLICATED_EMAIL(CONFLICT, 3000, "중복된 이메일이 존재합니다."),
    NONE_USER(NOT_FOUND, 3001, "존재하지 않는 회원입니다."),

    // google places exception
    INVALID_SEARCH_WORD(BAD_REQUEST, 4000, "검색어를 입력하지 않았습니다."),
    NOT_EXISTED_NEXT_PAGE_TOKEN(BAD_REQUEST, 4001, "NEXT PAGE TOKEN 이 존재하지 않습니다."),
    NONE_PHOTO_URL(NOT_FOUND, 4002, "PHOTO URL이 존재하지 않습니다."),
    NOT_EXISTED_PLACE_ID(BAD_REQUEST, 4003, "PLACE ID 가 존재하지 않습니다."),
    NOT_EXISTED_DETAILS_RESPONSE(BAD_REQUEST, 4004, "Details Response 가 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
