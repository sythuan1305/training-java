package com.beetech.trainningJava.service;

import com.beetech.trainningJava.model.ProductInforModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface chứa các phương thức xử lý file csv
 * @see com.beetech.trainningJava.service.imp.CSVServiceImp
 */
public interface ICSVService {
    /**
     * Đọc file csv và chuyển thành list product infor model
     * @param file là file csv cần đọc
     * @return  list product infor model sau khi đọc file csv
     */
    List<ProductInforModel> csvToProductInforModelList(MultipartFile file);
}
