package com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.Cache;
import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.CachedObject;
import com.getjavajob.training.wiley.andrianova.twolevelcache.strategy.CacheStrategy;
import com.getjavajob.training.wiley.andrianova.twolevelcache.strategy.CacheStrategyLru;
import com.getjavajob.training.wiley.andrianova.twolevelcache.strategy.CacheStrategyMru;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 16.02.2016.
 */
public class MemoryCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MemoryCache.class);
    private Map<K, CachedObject<K, V>> cache;
    private int capacity;

    public MemoryCache(CacheStrategyType cacheStrategyType, int maxSize) {
        CacheStrategy<K, V> cacheStrategy = initCacheStrategy(cacheStrategyType);
        logger.debug("Memory cache strategy: " + cacheStrategyType.name());
        this.capacity = maxSize;
        logger.debug("Memory cache capacity: " + capacity);
        this.cache = cacheStrategy.getMap(capacity);
        logger.debug("Memory cache created");
    }

    protected CacheStrategy<K, V> initCacheStrategy(CacheStrategyType cacheStrategyType) {
        switch (cacheStrategyType) {
            case LRU:
                return new CacheStrategyLru<K, V>() {
                    @Override
                    protected boolean removeExcessEntry(Map.Entry<K, CachedObject<K, V>> entry) {
                        if (getRemoveExcessEntryCondition()) {
                            additionalAction(entry.getKey());
                            logger.debug("Removed object, key: {}", entry.getKey());
                            return true;
                        }
                        return false;
                    }
                };
            case MRU:
                return new CacheStrategyMru<K, V>() {
                    @Override
                    protected boolean removeExcessEntry(Map.Entry<K, CachedObject<K, V>> entry) {
                        if (getRemoveExcessEntryCondition()) {
                            K key = getMruKey();
                            additionalAction(key);
                            cache.remove(key);
                            logger.debug("Removed object, key: {}", key);
                        }
                        return false;
                    }
                };
        }
        return null;
    }

    protected boolean getRemoveExcessEntryCondition() {
        return cache.size() > capacity;
    }

    protected void additionalAction(K key) {
    }

    private K getMruKey() {
        K key = null;
        Iterator<K> iterator = cache.keySet().iterator();
        for (int i = 0; i < cache.size() - 1; i++) {
            key = iterator.next();
        }
        return key;
    }

    @Override
    public V get(K key) {
        logger.debug("Going to get object with key: {}", key);
        V val = null;
        if (cache.containsKey(key)) {
            val = cache.get(key).getVal();
            logger.debug("Got object, value: {}", val);
        } else {
            logger.debug("There is no such object with key: {}", key);
        }
        return val;
    }

    @Override
    public void put(K key, V val) {
        logger.debug("Going to add object");
        CachedObject<K, V> cachedObject = new CachedObject<>(key, val);
        if (cachedObject.isValid()) {
            cache.put(key, cachedObject);
            logger.debug("Added object: {}", cachedObject);
        } else {
            logger.error("Object {} is not valid", cachedObject);
        }
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean remove(K key) {
        return cache.remove(key) != null;
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
