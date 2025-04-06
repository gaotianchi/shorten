package com.github.gaotianchi.shorten.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.gaotianchi.shorten.document.ShortLink;
import com.github.gaotianchi.shorten.exception.GlobalIdException;
import com.github.gaotianchi.shorten.repository.ShortLinkRepository;
import com.github.gaotianchi.shorten.service.CoreService;
import com.github.gaotianchi.shorten.utils.Base62Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author gaotianchi
 * @since 2025/3/24 下午3:51
 **/
@Service("coreService")
public class CacheableCoreServiceImpl implements CoreService {

    private final RedisTemplate<String, String> redisTemplate;
    private final Cache<String, String> caffeineCache;
    private final ShortLinkRepository shortLinkRepository;

    public CacheableCoreServiceImpl(
            RedisTemplate<String, String> redisTemplate,
            Cache<String, String> caffeineCache,
            ShortLinkRepository shortLinkRepository
    ) {
        this.redisTemplate = redisTemplate;
        this.caffeineCache = caffeineCache;
        this.shortLinkRepository = shortLinkRepository;
    }

    @Override
    public String generateShortCode() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Long id = ops.increment("global:id");
        if (id == null) {
            throw new GlobalIdException("获取全局ID失败");
        }
        return Base62Converter.decToBase62(id);
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        // 1. 优先从本地内存中获取数据
        String originalUrl = caffeineCache.getIfPresent(shortCode);
        if (originalUrl != null) {
            return originalUrl;
        }

        // 2. 如果本地内存中没有，则从Redis中获取数据
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        originalUrl = ops.get(shortCode);
        if (originalUrl != null) {
            // 如果Redis中有，则将数据放入本地内存中
            caffeineCache.put(shortCode, originalUrl);
            return originalUrl;
        }

        // 3. 如果Redis中也没有，则从数据库中获取数据
        ShortLink shortLink = shortLinkRepository.findByShortCode(shortCode);
        if (shortLink == null) {
            return null;
        }
        originalUrl = shortLink.getOriginalUrl();
        // 如果数据库中有，则将数据放入Redis和本地内存中
        ops.set(shortCode, originalUrl);
        caffeineCache.put(shortCode, originalUrl);
        return originalUrl;
    }

    @Override
    public void deleteLink(String shortCode) {

        // 1. 从本地内存中删除数据
        caffeineCache.invalidate(shortCode);

        // 2. 从Redis中删除数据
        redisTemplate.delete(shortCode);

        // 3. 从数据库中删除数据
        shortLinkRepository.deleteShortLinkByShortCode(shortCode);
    }
}
