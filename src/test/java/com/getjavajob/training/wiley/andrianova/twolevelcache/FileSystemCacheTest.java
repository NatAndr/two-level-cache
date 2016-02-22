package com.getjavajob.training.wiley.andrianova.twolevelcache;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel.FileSystemCache;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 18.02.2016.
 */
public class FileSystemCacheTest {
    private static final int CAPACITY = 3;
    private static final String DIR = "D:/dev/cache";
    private FileSystemCache<String, String> fileSystemCache;
    private File dir = new File(DIR);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void putNullKeyShouldTestExceptionMessage() throws IllegalArgumentException {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Null key is not allowed");
        fileSystemCache.put(null, "A");
    }

    @Test
    public void putNullValShouldTestExceptionMessage() throws IllegalArgumentException {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Null value is not allowed");
        fileSystemCache.put("A", null);
    }

    @Test
    public void testPut() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        fileSystemCache.put("C", "CCC");
        assertEquals(CAPACITY, fileSystemCache.getSize());
        assertEquals(CAPACITY, dir.listFiles().length);
    }

    @Test
    public void testDeletedExcess() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        fileSystemCache.put("C", "CCC");
        fileSystemCache.put("D", "DDD");
        assertEquals(CAPACITY, fileSystemCache.getSize());
        assertEquals(CAPACITY, dir.listFiles().length);
    }

    @Test
    public void testLruStrategy() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        fileSystemCache.put("C", "CCC");
        fileSystemCache.put("D", "DDD");
        assertTrue(!fileSystemCache.containsKey("A"));
    }

    @Test
    public void testMruStrategy() {
        String strategy = "MRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        fileSystemCache.put("C", "CCC");
        fileSystemCache.put("D", "DDD");
        assertTrue(!fileSystemCache.containsKey("C"));
        assertEquals(CAPACITY, dir.listFiles().length);
    }


    @Test
    public void testGet() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        assertEquals("BBB", fileSystemCache.get("B"));
    }

    @Test
    public void testGetNullKey() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        assertEquals(null, fileSystemCache.get(null));
    }

    @Test
    public void testContainsKeyTrue() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        assertTrue(fileSystemCache.containsKey("A"));
    }

    @Test
    public void testContainsKeyFalse() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        assertTrue(!fileSystemCache.containsKey("C"));
    }

    @Test
    public void testGetSize() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        assertEquals(2, fileSystemCache.getSize());
    }

    @Test
    public void testRemove() {
        String strategy = "LRU";
        fileSystemCache = new FileSystemCache<>(strategy, CAPACITY);
        fileSystemCache.put("A", "AAA");
        fileSystemCache.put("B", "BBB");
        fileSystemCache.remove("A");
        assertTrue(!fileSystemCache.containsKey("A"));
        assertEquals(1, dir.listFiles().length);
    }

}
