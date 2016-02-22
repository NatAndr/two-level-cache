package com.getjavajob.training.wiley.andrianova.twolevelcache;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel.TwoLevelCache;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 18.02.2016.
 */
public class TwoLevelCacheTest {
    private static final int LEVEL2_CACHE_CAPACITY = 3;
    private static final String DIR = "D:/dev/cache";
    private TwoLevelCache<String, String> cache;
    private File dir = new File(DIR);

    @Before
    public void init() throws Exception {
        cache = new TwoLevelCache<>();
    }

    @Test
    public void testMovingFromCache1ToCache2() {
        cache.put("A", "AAA");
        cache.put("B", "BBB");
        cache.put("C", "CCC");
        cache.put("D", "DDDD");
        cache.get("A");
        cache.put("E", "EEEE");
        assertTrue(cache.getCache1().containsKey("E"));
        assertTrue(cache.getCache1().containsKey("A"));
        assertTrue(cache.getCache2().containsKey("B"));
        assertTrue(cache.getCache2().containsKey("C"));
        assertTrue(cache.getCache2().containsKey("D"));
        assertEquals(LEVEL2_CACHE_CAPACITY, dir.listFiles().length);
    }

    @Test
    public void testPut() {
        cache.put("A", "AAA");
        cache.put("B", "BBB");
        assertTrue(cache.getCache1().containsKey("A"));
        assertTrue(cache.getCache1().containsKey("B"));
    }

    @Test
    public void testGet() {
        cache.put("A", "AAA");
        cache.put("B", "BBB");
        assertEquals("AAA", cache.get("A"));
    }
}
