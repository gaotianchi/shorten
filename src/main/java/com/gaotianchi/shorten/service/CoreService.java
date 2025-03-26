package com.gaotianchi.shorten.service;

/**
 * @author gaotianchi
 * @since 2025/3/24 下午3:51
 **/
public interface CoreService {

    String generateShortCode();

    String getOriginalUrl(String shortCode);
}
