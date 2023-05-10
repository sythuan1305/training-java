package com.beetech.trainningJava.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Utils {
    public final static String DEFAULT_COOKIE_VALUE = "defaultCookieValue";

    private static final int KBinBytes = 1024;

    public static long memoryUsed() {
        return ((Runtime.getRuntime().totalMemory() / KBinBytes) - (Runtime
                .getRuntime().freeMemory() / KBinBytes));
    }

    public static void performGC() {
        for (int i = 0; i < 10; i++) {
            System.gc();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
        }
    }

    public static Map<String, Object> JsonParserObjectWithEncodedBase64(String cookieValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(cookieValue);
        try {
            return new LinkedHashMap<>(new JSONParser(new String(decodedBytes)).parseObject());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Map<String, Object>> JsonParserListObjectWithEncodedURL(String cookieValue) {
        if (Utils.DEFAULT_COOKIE_VALUE.equals(cookieValue)) {
            return null;
        }

        String decodeURL = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
        System.out.println("decodeURL: " + decodeURL);
        try {
            return new ArrayList<>((List<Map<String, Object>>) new JSONParser(decodeURL).parse());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // ---------

    public static String JsonParserString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // ---------

    public static String ChangeVndToUsd(String vnd) {
        return checkRoundDirection(BigDecimal.valueOf(Double.parseDouble(vnd) / 23000)).toString();
    }

    public static BigDecimal checkRoundDirection(BigDecimal number) {
        BigDecimal roundedUp = number.setScale(2, RoundingMode.CEILING);
        BigDecimal roundedDown = number.setScale(2, RoundingMode.FLOOR);
        if (number.subtract(roundedDown).compareTo(roundedUp.subtract(number)) < 0) {
            return roundedDown;
        } else {
            return roundedUp;
        }
    }

    // ---------
    public static void deleteCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", "");
        cookie.setMaxAge(0); // Đặt thời gian hết hạn của cookie vào quá khứ
        cookie.setPath("/"); // Đảm bảo cookie được gửi đến tất cả các đường dẫn trên trang web
        response.addCookie(cookie); // Thêm cookie vào phản hồi (response)
    }

    public static class Base64Image {
        public static String path;

        /**
         * Lưu ảnh vào thư mục và trả về đường dẫn của ảnh
         * @param file là ảnh cần lưu
         * @return đường dẫn của ảnh sau khi lưu
         */
        public static String uploadImageByProductId(MultipartFile file, Integer productId) {
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

        /**
         * Lưu nhiều ảnh vào thư mục và trả về danh sách đường dẫn của ảnh
         * @param files là danh sách ảnh cần lưu
         * @return danh sách đường dẫn của ảnh sau khi lưu
         */
        public static List<String> uploadMultipleImagesByProductId(List<MultipartFile> files, Integer productId) {
            List<String> list = new ArrayList<>();
            for (MultipartFile file : files) {
                list.add(uploadImageByProductId(file, productId));
            }
            return list;
        }

        /**
         * Lấy ảnh theo đường dẫn
         * @param path là đường dẫn của ảnh cần lấy
         * @return ảnh được mã hóa base64
         */
        public static String getImageByPath(String path) {
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

        /**
         * Lấy nhiều ảnh theo danh sách đường dẫn
         * @param paths là danh sách đường dẫn của ảnh cần lấy
         * @return danh sách ảnh được mã hóa base64
         */
        public static List<String> getImageListByPathLists(List<String> paths) {
            List<String> images = new ArrayList<>();
            for (String path : paths) {
                images.add(getImageByPath(path));
            }
            return images;
        }

        /**
         * Chuyển base64 thành MultipartFile
         * @param base64 là ảnh được mã hóa base64
         * @return MultipartFile là ảnh sau khi chuyển
         */
        public MultipartFile convertBase64ImageToMultipartFile(String base64) {
            // https://stackoverflow.com/questions/23979842/convert-base64-string-to-image
            String[] base64Image = base64.split(",");
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image[1]);

            return new BASE64DecodedMultipartFile(imageBytes, "image_" + System.currentTimeMillis());
        }

        /**
         * Chuyển danh sách base64 thành danh sách MultipartFile
         * @param base64List là danh sách ảnh được mã hóa base64
         * @return danh sách MultipartFile là danh sách ảnh sau khi chuyển
         */
        public List<MultipartFile> convertBase64ImageListToMultipartFileList(List<String> base64List) {
            return base64List.stream().map(this::convertBase64ImageToMultipartFile).toList();
        }
    }
}


