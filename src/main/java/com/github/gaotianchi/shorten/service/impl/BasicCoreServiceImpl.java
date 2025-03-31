package com.github.gaotianchi.shorten.service.impl;

import com.github.gaotianchi.shorten.document.ShortLink;
import com.github.gaotianchi.shorten.exception.DocumentNotFoundException;
import com.github.gaotianchi.shorten.exception.GlobalIdException;
import com.github.gaotianchi.shorten.repository.ShortLinkRepository;
import com.github.gaotianchi.shorten.service.CoreService;
import com.github.gaotianchi.shorten.utils.Base62Converter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author gaotianchi
 * @since 2025/3/31 上午10:31
 **/
@Profile("v1.0")
@Service("coreService")
public class BasicCoreServiceImpl implements CoreService {

    private final ShortLinkRepository shortLinkRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public BasicCoreServiceImpl(
            ShortLinkRepository shortLinkRepository,
            RedisTemplate<String, String> redisTemplate
    ) {
        this.shortLinkRepository = shortLinkRepository;
        this.redisTemplate = redisTemplate;
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
        ShortLink shortLink = shortLinkRepository.findByShortCode(shortCode);
        if (shortLink == null) {
            throw new DocumentNotFoundException("ShortLink");
        }
        return shortLink.getOriginalUrl();
    }

    @Override
    public void deleteLink(String shortCode) {
        shortLinkRepository.deleteShortLinkByShortCode(shortCode);
    }
}
