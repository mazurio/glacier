package io.mazur.glacier;

import io.mazur.glacier.file.FileObjectPersister;

public class Glacier {
    private FileObjectPersister mFileObjectPersister;

    public interface Callback<T> {
        T onCacheNotFound();
    }

    public Glacier() {
        mFileObjectPersister = new FileObjectPersister();
    }

    public <T> T get(String cacheKey, Class<T> dataType, Callback<T> callback) {
        // if cacheKey not found
        // then
        T data = callback.onCacheNotFound();

        // persist data using FileObjectPersister

//        mFileObjectPersister.putDataInCache(cacheKey, data);

        return data;
    }
}
