package com.beetech.trainningJava.utils;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class BASE64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;

    private final String name;

    public BASE64DecodedMultipartFile(byte[] imgContent, String name) {
        this.imgContent = imgContent;
        this.name = name;
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return name;
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte @NonNull [] getBytes() {
        return imgContent;
    }

    @Override
    public @NonNull InputStream getInputStream() {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (OutputStream o = new FileOutputStream(dest)) {
            o.write(imgContent);
        }
    }
}
