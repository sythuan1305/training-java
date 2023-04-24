package com.beetech.trainningJava.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Interface chứa các phương thức xử lý file
 * @see com.beetech.trainningJava.service.imp.FileServiceImp
 */
public interface IFileService {
    /**
     * Lưu ảnh vào thư mục và trả về đường dẫn của ảnh
     * @param file là ảnh cần lưu
     * @return đường dẫn của ảnh sau khi lưu
     */
    public String uploadImageByProductId(MultipartFile file, Integer productId);

    /**
     * Lưu nhiều ảnh vào thư mục và trả về danh sách đường dẫn của ảnh
     * @param files là danh sách ảnh cần lưu
     * @return danh sách đường dẫn của ảnh sau khi lưu
     */
    public List<String> uploadMultipleImagesByProductId(List<MultipartFile> files, Integer productId);

    /**
     * Lấy ảnh theo đường dẫn
     * @param path là đường dẫn của ảnh cần lấy
     * @return ảnh được mã hóa base64
     */
    public String getImageByPath(String path) throws IOException;

    /**
     * Lấy nhiều ảnh theo danh sách đường dẫn
     * @param paths là danh sách đường dẫn của ảnh cần lấy
     * @return danh sách ảnh được mã hóa base64
     */
    public List<String> getImageListByPathLists(List<String> paths) throws IOException;

    /**
     * Chuyển base64 thành MultipartFile
     * @param base64 là ảnh được mã hóa base64
     * @return MultipartFile là ảnh sau khi chuyển
     */
    public MultipartFile convertBase64ImageToMultipartFile(String base64) throws IOException;

    /**
     * Chuyển danh sách base64 thành danh sách MultipartFile
     * @param base64List là danh sách ảnh được mã hóa base64
     * @return danh sách MultipartFile là danh sách ảnh sau khi chuyển
     */
    public List<MultipartFile> convertBase64ImageListToMultipartFileList(List<String> base64List) throws IOException;

}
