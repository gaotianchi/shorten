package com.gaotianchi.shorten.service;

import com.gaotianchi.shorten.document.ShortLink;

/**
 * @author gaotianchi
 * @since 2025/3/22 下午6:07
 **/
public interface ShortLinkService {
    ShortLink createShortLink(String originalUrl);

    ShortLink getShortLinkByShortCode(String shortCode);
}
