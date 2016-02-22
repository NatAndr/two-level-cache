package com.getjavajob.training.wiley.andrianova.twolevelcache.strategy;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.CachedObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by user on 16.02.2016.
 */
public interface CacheStrategy<K extends Serializable, V extends Serializable> {

    Map<K, CachedObject<K, V>> getMap(int capacity);
}
