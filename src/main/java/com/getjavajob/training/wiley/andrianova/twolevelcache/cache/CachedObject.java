package com.getjavajob.training.wiley.andrianova.twolevelcache.cache;

import java.io.Serializable;

/**
 * Created by user on 16.02.2016.
 */
public class CachedObject<K extends Serializable, V extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private K key;
    private V val;

    public CachedObject(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public V getVal() {
        return val;
    }

    public boolean isValid() {
        if (this.key == null) {
            throw new IllegalArgumentException("Null key is not allowed");
        } else {
            if (this.val == null) {
                throw new IllegalArgumentException("Null value is not allowed");
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CachedObject<?, ?> cachedObject = (CachedObject<?, ?>) o;

        return key.equals(cachedObject.key);

    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "{" + key + ", " + val + '}';
    }
}
