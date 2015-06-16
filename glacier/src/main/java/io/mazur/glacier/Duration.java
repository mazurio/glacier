package io.mazur.glacier;

public interface Duration {
    /**
     * Always ignore cache and treat it as expired (callback is executed).
     */
    long ALWAYS_EXPIRED = -1;

    /**
     * We do not care how old the cache is, we just want to return it unless there is completely
     * nothing there.
     */
    long ALWAYS_RETURNED = 0;

    long ONE_SECOND = 1000;

    long ONE_MINUTE = 60 * ONE_SECOND;
    long TWO_MINUTES = 2 * ONE_MINUTE;
    long THREE_MINUTES = 3 * ONE_MINUTE;
    long FOUR_MINUTES = 4 * ONE_MINUTE;
    long FIVE_MINUTES = 5 * ONE_MINUTE;

    long ONE_HOUR = 60 * ONE_MINUTE;
    long ONE_DAY = 24 * ONE_HOUR;
    long ONE_WEEK = 7 * ONE_DAY;
}
