package com.gaotianchi.shorten.service.impl;

import com.gaotianchi.shorten.document.ShortLink;
import com.gaotianchi.shorten.exception.DocumentNotFoundException;
import com.gaotianchi.shorten.repository.ShortLinkRepository;
import com.gaotianchi.shorten.service.LinkCacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author gaotianchi
 * @since 2025/3/26 下午4:31
 **/
@Service("linkCacheService")
public class LinkCacheServiceImpl implements LinkCacheService {

    private final ShortLinkRepository shortLinkRepository;

    public LinkCacheServiceImpl(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    @Override
    @Cacheable(value = "link", key = "#shortCode")
    public String getLink(String shortCode) {
        ShortLink shortLink = shortLinkRepository.findByShortCode(shortCode);
        if (shortLink == null) {
            throw new DocumentNotFoundException("ShortLink");
        }
        return shortLink.getOriginalUrl();
    }

    @Override
    @CacheEvict(value = "link", key = "#shortCode")
    public void deleteLink(String shortCode) {
        shortLinkRepository.deleteShortLinkByShortCode(shortCode);
    }
}
