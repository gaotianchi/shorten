package com.github.gaotianchi.shorten.exception;

/**
 * @author gaotianchi
 * @since 2025/3/26 上午7:58
 **/
public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(String documentName) {
        super(String.format("%s 没有找到", documentName));
    }
}
