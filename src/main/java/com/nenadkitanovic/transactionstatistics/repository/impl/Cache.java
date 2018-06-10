package com.nenadkitanovic.transactionstatistics.repository.impl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple cache implementation with size based eviction (first in first out of cache)... We could implement TTL eviction which expire after 60sec
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> extends LinkedHashMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int cacheSize;

    public Cache(int cacheSize) {
        super(cacheSize, LOAD_FACTOR, false);     //false=insertion order
        this.cacheSize = cacheSize;
    }

    /**
     * Remove the oldest entries from cache (first inserted) when the cache is full
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}