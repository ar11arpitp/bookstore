package com.online.bookstore.common.constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AppConstants {

    public static final String UTILITY_CLASS = "Utility class";

    public static class DateFormat {
        public static final String DATE_PATTERN = "yyyy-MM-dd";
        public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

        private DateFormat() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static final class Regex {

        public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";

        private Regex() {
            log.info(UTILITY_CLASS);
        }
    }

    public static class Messages {
        public static final String NOT_NULL_ERROR_MESSAGE = "must not be null";
        public static final String NOT_BLANK_ERROR_MESSAGE = "must not be Blank";

        private Messages() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static final class Characters {
        public static final String PERCENTAGE = "%";

        private Characters() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static final class Authentication {

        private Authentication() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    private AppConstants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
