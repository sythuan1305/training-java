package com.beetech.trainningJava.service;

import com.beetech.trainningJava.model.ProductInforModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICSVService {
    List<ProductInforModel> csvToProductInforModelList(MultipartFile file);
}
