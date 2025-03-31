package com.github.gaotianchi.shorten.service.impl;

import com.github.gaotianchi.shorten.document.ShortLink;
import com.github.gaotianchi.shorten.repository.ShortLinkRepository;
import com.github.gaotianchi.shorten.service.CoreService;
import com.github.gaotianchi.shorten.service.ShortLinkService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * @author gaotianchi
 * @since 2025/3/22 下午6:18
 **/
@Service("shortLinkService")
public class ShortLinkServiceImpl implements ShortLinkService {

    private final ShortLinkRepository shortLinkRepository;
    private final CoreService coreService;

    public ShortLinkServiceImpl(
            ShortLinkRepository shortLinkRepository,
            CoreService coreService
    ) {
        this.shortLinkRepository = shortLinkRepository;
        this.coreService = coreService;
    }

    @Override
    public ShortLink createShortLink(String originalUrl) {

        long expirationDate = Instant
                .now()
                .plusSeconds(TimeUnit.DAYS.toSeconds(30))
                .getEpochSecond()
                ;

        String shortCode = coreService.generateShortCode();

        ShortLink shortLink = ShortLink
                .builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .expirationDate(expirationDate)
                .build()
                ;

        return shortLinkRepository.save(shortLink);
    }

    @Override
    public ShortLink getShortLinkByShortCode(String shortCode) {
        String originalUrl = coreService.getOriginalUrl(shortCode);
        return ShortLink
                .builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .build();
    }

    @Override
    public void deleteShortLinkByShortCode(String shortCode) {
        coreService.deleteLink(shortCode);
    }
}
