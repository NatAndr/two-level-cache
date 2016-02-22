package com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.Cache;

import java.io.Serializable;

import static com.getjavajob.training.wiley.andrianova.twolevelcache.utils.CacheUtils.getProperty;

/**
 * Created by user on 16.02.2016.
 */
public class TwoLevelCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private static final String CACHE_MEMORY_STRATEGY = "cache.memory.Strategy";
    private static final String CACHE_MEMORY_MAX_SIZE = "cache.memory.MaxSize";
    private static final String CACHE_FILESYSTEM_STRATEGY = "cache.filesystem.Strategy";
    private static final String CACHE_FILESYSTEM_MAX_SIZE = "cache.filesystem.MaxSize";
    private Cache<K, V> cache1;
    private Cache<K, V> cache2;

    public TwoLevelCache() {
        initCaches();
    }

    public Cache<K, V> getCache1() {
        return cache1;
    }

    public Cache<K, V> getCache2() {
        return cache2;
    }

    private void initCaches() {
        final int memoryCacheCapacity = Integer.parseInt(getProperty(CACHE_MEMORY_MAX_SIZE));
        String memoryStrategy = getProperty(CACHE_MEMORY_STRATEGY).toUpperCase();
        cache1 = new MemoryCache<K, V>(CacheStrategyType.valueOf(memoryStrategy), memoryCacheCapacity) {
            @Override
            protected boolean getRemoveExcessEntryCondition() {
                return cache1.getSize() > memoryCacheCapacity;
            }

            @Override
            protected void additionalAction(K key) {
                cache2.put(key, cache1.get(key));
            }
        };
        cache2 = new FileSystemCache<>(getProperty(CACHE_FILESYSTEM_STRATEGY), Integer.parseInt(getProperty(CACHE_FILESYSTEM_MAX_SIZE)));
    }

    @Override
    public V get(K key) {
        V val = cache1.get(key);
        if (val == null) {
            val = cache2.get(key);
            if (val != null) {
                cache1.put(key, val);
                cache2.remove(key);
            }
        }
        return val;
    }

    @Override
    public void put(K key, V val) {
        cache1.put(key, val);
    }

    @Override
    public int getSize() {
        return cache1.getSize() + cache2.getSize();
    }

    @Override
    public boolean containsKey(K key) {
        return cache1.containsKey(key) || cache2.containsKey(key);
    }

    @Override
    public boolean remove(K key) {
        return cache1.remove(key) && cache2.remove(key);
    }

    @Override
    public String toString() {
        return "TwoLevelCache{level1{" + cache1 + "}, level2{" + cache2 + "}}";
    }
}
