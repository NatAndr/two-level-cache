package com.getjavajob.training.wiley.andrianova.twolevelcache;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel.CacheStrategyType;
import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel.MemoryCache;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 18.02.2016.
 */
public class MemoryCacheTest {
    private static final int CAPACITY = 3;
    private MemoryCache<String, String> memoryCache;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void putNullKeyShouldTestExceptionMessage() throws IllegalArgumentException {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Null key is not allowed");
        memoryCache.put(null, "A");
    }

    @Test
    public void putNullValShouldTestExceptionMessage() throws IllegalArgumentException {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Null value is not allowed");
        memoryCache.put("A", null);
    }

    @Test
    public void testPut() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        memoryCache.put("C", "CCC");
        assertEquals(CAPACITY, memoryCache.getSize());
    }

    @Test
    public void testDeletedExcess() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        memoryCache.put("C", "CCC");
        memoryCache.put("D", "DDD");
        assertEquals(CAPACITY, memoryCache.getSize());
    }

    @Test
    public void testLruStrategy() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        memoryCache.put("C", "CCC");
        memoryCache.put("D", "DDD");
        assertTrue(!memoryCache.containsKey("A"));
    }

    @Test
    public void testMruStrategy() {
        CacheStrategyType strategy = CacheStrategyType.MRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        memoryCache.put("C", "CCC");
        memoryCache.put("D", "DDD");
        assertTrue(!memoryCache.containsKey("C"));
    }


    @Test
    public void testGet() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        assertEquals("BBB", memoryCache.get("B"));
    }

    @Test
    public void testGetNullKey() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        assertEquals(null, memoryCache.get(null));
    }

    @Test
    public void testContainsKeyTrue() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        assertTrue(memoryCache.containsKey("A"));
    }

    @Test
    public void testContainsKeyFalse() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        assertTrue(!memoryCache.containsKey("C"));
    }

    @Test
    public void testGetSize() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        assertEquals(2, memoryCache.getSize());
    }

    @Test
    public void testRemove() {
        CacheStrategyType strategy = CacheStrategyType.LRU;
        memoryCache = new MemoryCache<>(strategy, CAPACITY);
        memoryCache.put("A", "AAA");
        memoryCache.put("B", "BBB");
        memoryCache.remove("A");
        assertTrue(!memoryCache.containsKey("A"));
    }
}
