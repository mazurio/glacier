package io.mazur.glacier.file;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;

import io.mazur.glacier.Duration;
import io.mazur.glacier.model.User;

public class FileObjectPersisterTest {
    String workingDirectory = System.getProperty("user.dir");

    File baseCacheDirectory;
    FileObjectPersister fileObjectPersister;

    @Before
    public void before() throws Exception {
        baseCacheDirectory = new File("cache");

        fileObjectPersister = new FileObjectPersister();
        fileObjectPersister.setCacheDirectory(baseCacheDirectory);
    }

    @After
    public void after() throws Exception {
//        fileObjectPersister.removeAllDataFromCache();
    }

    @Test
    public void createsCacheDirectoryTest() throws Exception {
        assertEquals(fileObjectPersister.getCacheDirectory().getAbsolutePath(),
                workingDirectory + "/cache/glacier-cache");

        assertTrue(fileObjectPersister.getCacheDirectory().exists());
        assertTrue(fileObjectPersister.getCacheDirectory().canWrite());
    }

    @Test
    public void putIntegerInCacheTest() throws Exception {
        fileObjectPersister.putDataInCache("int", 9);

        assertEquals(9, fileObjectPersister.getDataFromCache("int", Integer.class, Duration.ALWAYS_RETURNED));
    }

    @Test
    public void putStringInCacheTest() throws Exception {
        fileObjectPersister.putDataInCache("cacheKey", "value");

        assertEquals("value", fileObjectPersister.getDataFromCache("cacheKey", String.class, Duration.ALWAYS_RETURNED));
    }

    @Test
    public void putSerializableInCacheTest() throws Exception {
        User inCache = new User("Steve", "Jobs");

        fileObjectPersister.putDataInCache("stevej", inCache);

        User fromCache = (User) fileObjectPersister.getDataFromCache("stevej", User.class, Duration.ALWAYS_RETURNED);

        assertEquals("Steve", fromCache.getName());
        assertEquals("Jobs", fromCache.getSurname());
    }

    @Test
    public void putSerializableInCacheAndOverwriteTest() throws Exception {
        User inCache = new User("Steve", "Test");

        fileObjectPersister.putDataInCache("user", inCache);

        User fromCache = (User) fileObjectPersister.getDataFromCache("user", User.class, Duration.ALWAYS_RETURNED);

        assertEquals("Steve", fromCache.getName());
        assertEquals("Test", fromCache.getSurname());

        inCache.setName("John");
        inCache.setSurname("Nullable");

        fileObjectPersister.putDataInCache("user", inCache);

        fromCache = (User) fileObjectPersister.getDataFromCache("user", User.class, Duration.ALWAYS_RETURNED);

        assertEquals("John", fromCache.getName());
        assertEquals("Nullable", fromCache.getSurname());
    }

    @Test
    public void getNullObjectFromCacheTest() throws Exception {
        Object o = fileObjectPersister.getDataFromCache("random_key_that_does_not_exist", String.class, Duration.ALWAYS_RETURNED);

        assertEquals(null, o);
    }

    @Test
    public void putNullObjectInCacheTest() throws Exception {
        assertFalse(fileObjectPersister.putDataInCache("invalid", null));
        assertFalse(fileObjectPersister.putDataInCache(null, "data"));
        assertFalse(fileObjectPersister.putDataInCache(null, null));
    }

    @Test
    public void putMultipleObjectsInCacheTest() throws Exception {
        String path = fileObjectPersister.getCacheDirectory().getPath();

        fileObjectPersister.putDataInCache("key1", "value1");
        fileObjectPersister.putDataInCache("key2", "value2");

        String key1Name = fileObjectPersister.createFileName("key1", String.class);
        String key2Name = fileObjectPersister.createFileName("key2", String.class);

        assertTrue(FileUtils.directoryContains(fileObjectPersister.getCacheDirectory(),
                new File(path + "/" + key1Name)));
        assertTrue(FileUtils.directoryContains(fileObjectPersister.getCacheDirectory(),
                new File(path + "/" + key2Name)));
    }

    @Test
    public void createFileNameTest() throws Exception {
        assertEquals("Class.java.lang.String.With.Key.3288498",
                fileObjectPersister.createFileName("key1", String.class));

        assertEquals("Class.java.lang.String.With.Key.-1339119418",
                fileObjectPersister.createFileName("damian", String.class));

        assertEquals("Class.java.lang.Integer.With.Key.1431968",
                fileObjectPersister.createFileName("/  /", Integer.class));

        assertEquals("Class.java.lang.Integer.With.Key.1321580970",
                fileObjectPersister.createFileName("!@£$%^&*()_+", Integer.class));
    }
}
