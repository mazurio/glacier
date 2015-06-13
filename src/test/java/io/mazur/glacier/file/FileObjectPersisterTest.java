package io.mazur.glacier.file;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;

import io.mazur.glacier.Duration;
import io.mazur.glacier.file.model.User;

public class FileObjectPersisterTest {
    File baseCacheDirectory;
    FileObjectPersister fileObjectPersister;

    @Before
    public void before() throws Exception {
        baseCacheDirectory = new File("cache");

        fileObjectPersister = new FileObjectPersister();
        fileObjectPersister.setCacheDirectory(baseCacheDirectory);
    }

    @Test
    public void createsCacheDirectoryTest() throws Exception {
        assertEquals(fileObjectPersister.getCacheDirectory().getAbsolutePath(),
                "/Users/mazurd01/AndroidStudioProjects/BBCTravel/mobile/cache/glacier-cache");

        assertTrue(fileObjectPersister.getCacheDirectory().exists());
        assertTrue(fileObjectPersister.getCacheDirectory().canWrite());
    }

    @Test
    public void putIntegerInCacheTest() throws Exception {
        fileObjectPersister.putDataInCache("int", 9);

        assertEquals(fileObjectPersister.getDataFromCache("int", Duration.ALWAYS_RETURNED), 9);
    }

    @Test
    public void putStringInCacheTest() throws Exception {
        fileObjectPersister.putDataInCache("string", "GLACIER_STRING");

        assertEquals(fileObjectPersister.getDataFromCache("string", Duration.ALWAYS_RETURNED), "GLACIER_STRING");
    }

    @Test
    public void putSerializableInCacheTest() throws Exception {
        User inCache = new User("Steve", "Jobs");

        fileObjectPersister.putDataInCache("stevej", inCache);

        User fromCache = (User) fileObjectPersister.getDataFromCache("stevej", Duration.ALWAYS_RETURNED);

        assertEquals(fromCache.getName(), "Steve");
        assertEquals(fromCache.getSurname(), "Jobs");
    }

    @Test
    public void putSerializableInCacheAndOverwriteTest() throws Exception {
        User inCache = new User("Steve", "Test");

        fileObjectPersister.putDataInCache("user", inCache);

        User fromCache = (User) fileObjectPersister.getDataFromCache("user", Duration.ALWAYS_RETURNED);

        assertEquals(fromCache.getName(), "Steve");
        assertEquals(fromCache.getSurname(), "Test");

        inCache.setName("John");
        inCache.setSurname("Nullable");

        fileObjectPersister.putDataInCache("user", inCache);

        fromCache = (User) fileObjectPersister.getDataFromCache("user", Duration.ALWAYS_RETURNED);

        assertEquals(fromCache.getName(), "John");
        assertEquals(fromCache.getSurname(), "Nullable");
    }

    @Test
    public void getNullObjectFromCacheTest() throws Exception {
        Object o = fileObjectPersister.getDataFromCache("random_key_that_does_not_exist", Duration.ALWAYS_RETURNED);

        assertEquals(o, null);
    }

    @Test
    public void putNullObjectInCacheTest() throws Exception {
        assertFalse(fileObjectPersister.putDataInCache("invalid", null));
        assertFalse(fileObjectPersister.putDataInCache(null, "data"));
        assertFalse(fileObjectPersister.putDataInCache(null, null));
    }

    @After
    public void after() throws Exception {
        baseCacheDirectory.delete();
    }
}
