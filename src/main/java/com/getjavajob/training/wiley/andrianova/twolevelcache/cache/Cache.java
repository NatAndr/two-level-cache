package com.getjavajob.training.wiley.andrianova.twolevelcache.cache;

/**
 * Created by user on 16.02.2016.
 */
public interface Cache<K, V> {
    V get(K key);

    void put(K key, V val);

    int getSize();

    boolean containsKey(K key);

    boolean remove(K key);
}
