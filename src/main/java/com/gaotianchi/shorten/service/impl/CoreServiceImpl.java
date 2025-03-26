package com.gaotianchi.shorten.service.impl;

import com.gaotianchi.shorten.document.ShortLink;
import com.gaotianchi.shorten.exception.DocumentNotFoundException;
import com.gaotianchi.shorten.exception.GlobalIdException;
import com.gaotianchi.shorten.repository.ShortLinkRepository;
import com.gaotianchi.shorten.service.CoreService;
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
    private final ShortLinkRepository shortLinkRepository;

    public CoreServiceImpl(
            RedisTemplate<String, String> redisTemplate,
            ShortLinkRepository shortLinkRepository
    ) {
        this.redisTemplate = redisTemplate;
        this.shortLinkRepository = shortLinkRepository;
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
}
