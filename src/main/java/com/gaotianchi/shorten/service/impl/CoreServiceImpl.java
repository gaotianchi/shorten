package com.gaotianchi.shorten.service.impl;

import com.gaotianchi.shorten.exception.GlobalIdException;
import com.gaotianchi.shorten.service.CoreService;
import com.gaotianchi.shorten.service.LinkCacheService;
import com.gaotianchi.shorten.utils.Base62Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author gaotianchi
 * @since 2025/3/24 下午3:51
 **/
@Service("coreService")
public class CoreServiceImpl implements CoreService {

    private final RedisTemplate<String, String> redisTemplate;
    private final LinkCacheService linkCacheService;

    public CoreServiceImpl(
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
