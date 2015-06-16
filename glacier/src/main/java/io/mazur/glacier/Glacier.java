package io.mazur.glacier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import io.mazur.glacier.file.FileObjectPersister;

public class Glacier {
    public interface Callback<T> {
        T onCacheNotFound();
    }

    private static FileObjectPersister mFileObjectPersister;

    public synchronized static void init() {
        mFileObjectPersister = new FileObjectPersister();

        try {
            mFileObjectPersister.setCacheDirectory(new File("cache"));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public synchronized static void init(@NonNull Context context) {
        mFileObjectPersister = new FileObjectPersister();

        try {
            mFileObjectPersister.setCacheDirectory(context);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public synchronized static void removeAllDataFromCache() {
        mFileObjectPersister.removeAllDataFromCache();
    }

    /**
     * Put object into cache with given cache key used later to retrieve it.
     *
     * @param cacheKey cache key in format [a-z0-9].
     * @param data data type, e.g. String.class.
     */
    public synchronized static <T> void put(@NonNull String cacheKey, @NonNull T data) {
        try {
            mFileObjectPersister.putDataInCache(cacheKey, data);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Nullable
    public synchronized static <T> T get(@NonNull String cacheKey, @NonNull Class<T> dataType) {
        return (T) mFileObjectPersister.getDataFromCache(cacheKey, dataType, Duration.ALWAYS_RETURNED);
    }

    @Nullable
    public synchronized static <T> T get(@NonNull String cacheKey, @NonNull Class<T> dataType, @NonNull long cacheDuration) {
        return (T) mFileObjectPersister.getDataFromCache(cacheKey, dataType, cacheDuration);
    }

    @NonNull
    public synchronized static <T> T getOrElse(@NonNull String cacheKey, @NonNull Class<T> dataType,
                                  @NonNull long cacheDuration, @NonNull Callback<T> callback) {
        T cacheObject = (T) mFileObjectPersister.getDataFromCache(cacheKey, dataType, cacheDuration);

        if(cacheObject == null) {
            T persistObject = callback.onCacheNotFound();

            try {
                mFileObjectPersister.putDataInCache(cacheKey, persistObject);
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage());
            }

            return persistObject;
        } else {
            return cacheObject;
        }
    }
}
