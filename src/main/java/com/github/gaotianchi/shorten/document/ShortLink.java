package com.github.gaotianchi.shorten.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author gaotianchi
 * @since 2025/3/22 下午6:05
 **/
@Data
@Builder
@Document("short_link")
public class ShortLink {
    @Id
    private String id;
    private String originalUrl;
    private String shortCode;
    private long expirationDate;
}
