package com.github.gaotianchi.shorten.service;

/**
 * @author gaotianchi
 * @since 2025/3/26 下午4:29
 **/
public interface LinkCacheService {

    String getLink(String shortCode);

    void deleteLink(String shortCode);
}
