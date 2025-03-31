package com.github.gaotianchi.shorten.service.impl;

import com.github.gaotianchi.shorten.exception.GlobalIdException;
import com.github.gaotianchi.shorten.service.CoreService;
import com.github.gaotianchi.shorten.service.LinkCacheService;
import com.github.gaotianchi.shorten.utils.Base62Converter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author gaotianchi
 * @since 2025/3/24 下午3:51
 **/
@Profile("v1.1")
@Service("coreService")
public class CacheableCoreServiceImpl implements CoreService {

    private final RedisTemplate<String, String> redisTemplate;
    private final LinkCacheService linkCacheService;

    public CacheableCoreServiceImpl(
            RedisTemplate<String, String> redisTemplate,
            LinkCacheService linkCacheService
    ) {
        this.redisTemplate = redisTemplate;
        this.linkCacheService = linkCacheService;
    }

    @Override
    public String generateShortCode() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Long id = ops.increment("shorten:Id");
        if (id == null) {
            throw new GlobalIdException("获取全局ID失败");
        }
        return Base62Converter.decToBase62(id);
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        return linkCacheService.getLink(shortCode);
    }

    @Override
    public void deleteLink(String shortCode) {
        linkCacheService.deleteLink(shortCode);
    }
}
