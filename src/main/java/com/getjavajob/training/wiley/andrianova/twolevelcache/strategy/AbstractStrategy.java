package com.getjavajob.training.wiley.andrianova.twolevelcache.strategy;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.CachedObject;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 16.02.2016.
 */
public abstract class AbstractStrategy<K extends Serializable, V extends Serializable> {

    protected abstract boolean removeExcessEntry(Map.Entry<K, CachedObject<K, V>> entry);

    public Map<K, CachedObject<K, V>> getMap(int capacity) {
        return Collections.synchronizedMap(new LinkedHashMap<K, CachedObject<K, V>>(capacity, 1, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CachedObject<K, V>> eldest) {
                return removeExcessEntry(eldest);
            }
        });
    }
}
