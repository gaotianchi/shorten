package com.gaotianchi.shorten.service.impl;

import com.gaotianchi.shorten.service.CoreService;
import org.springframework.stereotype.Service;

/**
 * @author gaotianchi
 * @since 2025/3/24 下午3:51
 **/
@Service("coreService")
public class CoreServiceImpl implements CoreService {
    @Override
    public String generateShortCode() {
        return "";
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        return "";
    }
}
