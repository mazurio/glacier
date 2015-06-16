package io.mazur.glacier;

import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GlacierTest {
    @Before
    public void before() {
        Glacier.init();
    }

    @After
    public void after() {
        Glacier.removeAllDataFromCache();
    }

    @Test
    public void cacheNotFoundTest() {
        String cacheResult = Glacier.getOrElse("cacheKey", String.class, Duration.ONE_MINUTE, new Glacier.Callback<String>() {
            @Override
            public String onCacheNotFound() {
                return "cacheNotFound";
            }
        });

        assertEquals("cacheNotFound", cacheResult);
    }

    @Test
    public void putInCacheTest() throws Exception {
        String cacheKey = "cacheKey";
        String cacheValue = "value";

        Glacier.put(cacheKey, cacheValue);

        assertEquals(cacheValue, Glacier.get(cacheKey, String.class));
        assertEquals(cacheValue, Glacier.get(cacheKey, String.class, Duration.ALWAYS_RETURNED));
    }

    @Test
    public void putArrayListInCacheTest() throws Exception {
        ArrayList<String> stringArrayList = new ArrayList<>();

        stringArrayList.add("One");
        stringArrayList.add("Two");

        Glacier.put("list", stringArrayList);

        ArrayList<String> fromCache = new ArrayList<>();

        fromCache = Glacier.get("list", fromCache.getClass());

        assertEquals("One", fromCache.get(0));
        assertEquals("Two", fromCache.get(1));
    }
}
