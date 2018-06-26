package me.chulwoo.naver.blog.search.data.blog.service.exception;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class NaverApiError {

    String errorMessage;
    String errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static class ErrorCode {

        public static final String INCORRECT_QUERY_REQUEST = "SE01";
        public static final String INVALID_DISPLAY_VALUE = "SE02";
        public static final String INVALID_START_VALUE = "SE03";
        public static final String INVALID_SORT_VALUE = "SE04";
        public static final String MALFORMED_ENCODING = "SE05";
        public static final String INVALID_SEARCH_API = "SE06";
        public static final String SYSTEM_ERROR = "SE99";
    }

    public static class Status {

        /**
         * (요청 변수 확인)
         */
        public static final int BAD_REQUEST = 400;

        /**
         * 인증 실패
         */
        public static final int UNAUTHORIZED = 401;

        /**
         * 서버가 허용하지 않는 호출
         */
        public static final int FORBIDDEN = 403;

        /**
         * API 없음
         */
        public static final int NOT_FOUND = 404;

        /**
         * 메서드 허용 안 함
         */
        public static final int METHOD_NOT_ALLOWED = 405;

        /**
         * 호출 한도 초과 오류
         */

        public static final int TOO_MANY_REQUESTS = 429;

        /**
         * (서버 오류)
         */
        public static final int INTERNAL_SERVER_ERROR = 500;
    }
}