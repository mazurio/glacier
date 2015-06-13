package io.mazur.glacier.file;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.mazur.glacier.Duration;
import io.mazur.glacier.exception.CacheDirectoryCreationException;

public class FileObjectPersister {
    private static final String CACHE_DIRECTORY = "glacier-cache";

    private File mCacheDirectory;

    public void setCacheDirectory(Context context) throws CacheDirectoryCreationException {
        if(mCacheDirectory == null) {
            mCacheDirectory = new File(context.getCacheDir(), CACHE_DIRECTORY);
        }

        if(!mCacheDirectory.exists() && !mCacheDirectory.mkdirs()) {
            throw new CacheDirectoryCreationException("Cache Directory could not be created.");
        }
    }

    public void setCacheDirectory(File baseCacheDirectory) throws CacheDirectoryCreationException {
        if(mCacheDirectory == null) {
            mCacheDirectory = new File(baseCacheDirectory, CACHE_DIRECTORY);
        }

        if(!mCacheDirectory.exists() && !mCacheDirectory.mkdirs()) {
            throw new CacheDirectoryCreationException("Cache Directory could not be created.");
        }
    }

    public File getCacheDirectory() {
        return mCacheDirectory;
    }

    public <T> boolean putDataInCache(String cacheKey, T data) throws IOException {
        if(cacheKey == null || data == null) {
            return false;
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(mCacheDirectory, cacheKey));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(data);
            objectOutputStream.close();

            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return false;
    }

    public Object getDataFromCache(String cacheKey, long duration) {
        Object object = null;

        try {
            File file = new File(mCacheDirectory, cacheKey);

            if(!file.exists() || !isCacheValid(file.lastModified(), duration)) {
                return null;
            }

            FileInputStream fileInputStream = new FileInputStream(file);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            object = objectInputStream.readObject();

            objectInputStream.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return object;
    }

    public boolean isCacheValid(long fileLastModified, long duration) {
        long timeInCache = System.currentTimeMillis() - fileLastModified;

        return (duration == Duration.ALWAYS_RETURNED || timeInCache <= duration);
    }
}
