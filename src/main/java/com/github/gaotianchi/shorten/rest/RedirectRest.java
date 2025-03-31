package com.github.gaotianchi.shorten.rest;

import com.github.gaotianchi.shorten.service.CacheableCoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author gaotianchi
 * @since 2025/3/26 上午8:55
 **/
@RestController
public class RedirectRest {

    private final CacheableCoreService cacheableCoreService;

    public RedirectRest(CacheableCoreService cacheableCoreService) {
        this.cacheableCoreService = cacheableCoreService;
    }

    @GetMapping("{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable("shortCode") String shortCode
    ) throws URISyntaxException {
        String originalUrl = cacheableCoreService.getOriginalUrl(shortCode);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(new URI(originalUrl))
                .build();
    }
}
