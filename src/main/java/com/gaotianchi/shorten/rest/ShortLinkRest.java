package com.gaotianchi.shorten.rest;

import com.gaotianchi.shorten.document.ShortLink;
import com.gaotianchi.shorten.service.ShortLinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author gaotianchi
 * @since 2025/3/22 下午6:19
 **/
@RestController
@RequestMapping("links")
public class ShortLinkRest {

    private final ShortLinkService shortLinkService;

    public ShortLinkRest(
            ShortLinkService shortLinkService
    ) {
        this.shortLinkService = shortLinkService;
    }

    @PostMapping
    public ResponseEntity<ShortLink> createShortLink(
            @RequestParam("url") String originalUrl
    ) {
        ShortLink shortLink = shortLinkService.createShortLink(originalUrl);
        return ResponseEntity.ok(shortLink);
    }

    @GetMapping
    public ResponseEntity<ShortLink> getShortLinkByShortCode(
            @RequestParam("code") String shortCode
    ) {
        ShortLink shortLink = shortLinkService.getShortLinkByShortCode(shortCode);
        return ResponseEntity.ok(shortLink);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteShortLinkByShortCode(
            @RequestParam("code") String shortCode
    ) {
        shortLinkService.deleteShortLinkByShortCode(shortCode);
        return ResponseEntity
                .ok()
                .build();
    }
}
