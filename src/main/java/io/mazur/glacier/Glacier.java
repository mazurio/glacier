package io.mazur.glacier;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import io.mazur.glacier.file.FileObjectPersister;

public class Glacier {
    public interface Callback<T> {
        T onCacheNotFound();
    }

    private static FileObjectPersister mFileObjectPersister;

    public static void init() {
        mFileObjectPersister = new FileObjectPersister();

        try {
            mFileObjectPersister.setCacheDirectory(new File("cache"));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static void init(Context context) {
        mFileObjectPersister = new FileObjectPersister();

        try {
            mFileObjectPersister.setCacheDirectory(context);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static void removeAllDataFromCache() {
        mFileObjectPersister.removeAllDataFromCache();
    }

    public static <T> void put(String cacheKey, T data) {
        try {
            mFileObjectPersister.putDataInCache(cacheKey, data);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static <T> T get(String cacheKey, Class<T> dataType) {
        return (T) mFileObjectPersister.getDataFromCache(cacheKey, dataType, Duration.ALWAYS_RETURNED);
    }

    public static <T> T get(String cacheKey, Class<T> dataType, long cacheDuration) {
        return (T) mFileObjectPersister.getDataFromCache(cacheKey, dataType, cacheDuration);
    }

    public static <T> T getOrElse(String cacheKey, Class<T> dataType, long cacheDuration, Callback<T> callback) {
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
