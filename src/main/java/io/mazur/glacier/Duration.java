package io.mazur.glacier;

public interface Duration {
    long ALWAYS_RETURNED = 0;
    long ONE_SECOND = 1000;
    long ONE_MINUTE = 60 * ONE_SECOND;
    long ONE_HOUR = 60 * ONE_MINUTE;
    long ONE_DAY = 24 * ONE_HOUR;
    long ONE_WEEK = 7 * ONE_DAY;
}
