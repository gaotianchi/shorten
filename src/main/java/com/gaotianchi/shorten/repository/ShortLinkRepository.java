package com.gaotianchi.shorten.repository;

import com.gaotianchi.shorten.document.ShortLink;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gaotianchi
 * @since 2025/3/22 下午6:06
 **/
@Repository
public interface ShortLinkRepository extends MongoRepository<ShortLink, String> {
    ShortLink findByShortCode(String shortCode);

    void deleteShortLinkByShortCode(String shortCode);
}
