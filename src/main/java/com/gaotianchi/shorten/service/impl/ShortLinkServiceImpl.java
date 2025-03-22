package com.gaotianchi.shorten.service.impl;

import com.gaotianchi.shorten.document.ShortLink;
import com.gaotianchi.shorten.repository.ShortLinkRepository;
import com.gaotianchi.shorten.service.ShortLinkService;
import org.apache.commons.lang3.RandomStringUtils;
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

    public ShortLinkServiceImpl(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    @Override
    public ShortLink createShortLink(String originalUrl) {
        String shortCode = generateShortCode();
        long expirationDate = Instant
                .now()
                .plusSeconds(TimeUnit.DAYS.toSeconds(30))
                .getEpochSecond()
                ;

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
        return shortLinkRepository.findByShortCode(shortCode);
    }

    private String generateShortCode() {
        String shortCode;
        do {
            shortCode = RandomStringUtils.randomAlphanumeric(6);
        } while (shortLinkRepository.findByShortCode(shortCode) != null);
        return shortCode;
    }
}
