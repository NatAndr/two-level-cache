package com.getjavajob.training.wiley.andrianova.twolevelcache.strategy;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.CachedObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by user on 16.02.2016.
 */
public class CacheStrategyLru<K extends Serializable, V extends Serializable> extends AbstractStrategy<K, V> implements CacheStrategy<K, V> {

    @Override
    protected boolean removeExcessEntry(Map.Entry<K, CachedObject<K, V>> entry) {
        return true;
    }
}
