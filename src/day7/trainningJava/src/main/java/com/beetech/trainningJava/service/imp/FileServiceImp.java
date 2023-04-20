package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.service.IFileService;
import com.beetech.trainningJava.utils.BASE64DecodedMultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileServiceImp implements IFileService {

    @Value("${upload.path}")
    String path;

    @Override
    public String uploadImageByProductId(MultipartFile file, Integer productId) {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            var fileName = file.getOriginalFilename();
            var is = file.getInputStream();

            // Create Folder
            File dir = new File(path + "productId_" + productId);
            if (!dir.exists()) dir.mkdirs();

            var path = dir.getPath() + "\\" + System.currentTimeMillis() + "_" + fileName;
            Files.copy(is, Paths.get(path),
                    StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            var msg = String.format("Failed to store file " + file.getName());
            System.out.println(msg);
        }
        return "";
    }

    @Override
    public List<String> uploadMultipleImagesByProductId(List<MultipartFile> files, Integer productId) {
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            list.add(uploadImageByProductId(file, productId));
        }
        return list;
    }

    @Override
    public String getImageByPath(String path) throws IOException {
        File file = new File(path);
        BufferedImage img = ImageIO.read(file);
        if (img == null) {
            System.out.println("Failed to read image file");
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
        return "";
    }

    @Override
    public List<String> getImageListByPathLists(List<String> paths) throws IOException {
        List<String> images = new ArrayList<>();
        for (String path : paths) {
            images.add(getImageByPath(path));
        }
        return images;
    }

    @Override
    public MultipartFile convertBase64ImageToMultipartFile(String base64) throws IOException {
        String[] base64Image = base64.split(",");
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image[1]);

        return new BASE64DecodedMultipartFile(imageBytes, "image_" + System.currentTimeMillis());
    }

    @Override
    public List<MultipartFile> convertBase64ImageListToMultipartFileList(List<String> base64List) throws IOException {
        return base64List.stream().map(base64 -> {
            try {
                return convertBase64ImageToMultipartFile(base64);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).toList();
    }
}
