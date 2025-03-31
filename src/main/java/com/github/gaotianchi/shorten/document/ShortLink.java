package com.github.gaotianchi.shorten.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("short_link")
public class ShortLink {
    @Id private String id;

    @Indexed(unique = true) private String shortCode;

    private String originalUrl;

    private long expirationDate;
}
