package com.getjavajob.training.wiley.andrianova.twolevelcache;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel.TwoLevelCache;

/**
 * Created by user on 17.02.2016.
 */
public class Runner {

    public static void main(String[] args) {
        TwoLevelCache<String, String> cache = new TwoLevelCache<>();
        cache.put("A", "AAAA");
        cache.put("B", "BBBB");
        cache.put("C", "CCCC");
        System.out.println(cache);
        cache.get("A");
        cache.get("B");
        cache.get("A");
        cache.get("B");
        cache.put("D", "DDDD");
        System.out.println(cache);
    }
}
