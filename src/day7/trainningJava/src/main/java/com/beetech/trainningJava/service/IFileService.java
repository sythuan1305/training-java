package com.beetech.trainningJava.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IFileService {
    public String uploadFile(MultipartFile file, Integer productId);

    public List<String> uploadMultipleFiles(List<MultipartFile> files, Integer productId);

    public String getImage(String path) throws IOException;

    public List<String> getImages(List<String> paths) throws IOException;

    public MultipartFile convertBase64ImageToMultipartFile(String base64) throws IOException;

    public List<MultipartFile> convertBase64ImageListToMultipartFileList(List<String> base64List) throws IOException;

}
