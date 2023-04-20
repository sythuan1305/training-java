package com.beetech.trainningJava.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileService {
    public String uploadImageByProductId(MultipartFile file, Integer productId);

    public List<String> uploadMultipleImagesByProductId(List<MultipartFile> files, Integer productId);

    public String getImageByPath(String path) throws IOException;

    public List<String> getImageListByPathLists(List<String> paths) throws IOException;

    public MultipartFile convertBase64ImageToMultipartFile(String base64) throws IOException;

    public List<MultipartFile> convertBase64ImageListToMultipartFileList(List<String> base64List) throws IOException;

}
