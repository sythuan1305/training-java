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

/**
 * Class này dùng để triển khai các phương thức của interface IFileService
 *
 * @see com.beetech.trainningJava.service.IFileService
 */
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
        InputStream inputStream = null;
        try (InputStream is = file.getInputStream()) {
            // Tạo thư mục theo productId
            File dir = new File(path + "productId_" + productId);
            if (!dir.exists()) dir.mkdirs();

            // Lấy tên file và tạo path
            var fileName = file.getOriginalFilename();
            var path = dir.getPath() + "\\" + System.currentTimeMillis() + "_" + fileName;

            // Lưu file vào path
            // Nếu file đã tồn tại thì ghi đè
            Files.copy(is, Paths.get(path),
                    StandardCopyOption.REPLACE_EXISTING);
            inputStream = is;
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
    public String getImageByPath(String path) {
        // Lấy file từ path
        File file = new File(path);
        // Đọc file thành BufferedImage
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
            if (img == null) {
                System.out.println("Failed to read image file");
            } else {
                // Chuyển BufferedImage thành byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", baos);
                // Chuyển byte array thành chuỗi base64
                return Base64.getEncoder().encodeToString(baos.toByteArray());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    @Override
    public List<String> getImageListByPathLists(List<String> paths) {
        List<String> images = new ArrayList<>();
        for (String path : paths) {
            images.add(getImageByPath(path));
        }
        return images;
    }

    @Override
    public MultipartFile convertBase64ImageToMultipartFile(String base64) {
        // https://stackoverflow.com/questions/23979842/convert-base64-string-to-image
        String[] base64Image = base64.split(",");
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image[1]);

        return new BASE64DecodedMultipartFile(imageBytes, "image_" + System.currentTimeMillis());
    }

    @Override
    public List<MultipartFile> convertBase64ImageListToMultipartFileList(List<String> base64List) {
        return base64List.stream().map(this::convertBase64ImageToMultipartFile).toList();
    }
}
