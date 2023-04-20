package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.ICSVService;
import com.beetech.trainningJava.service.IFileService;
import com.beetech.trainningJava.service.IProductImageUrlService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CSVServiceImp implements ICSVService {
    @Autowired
    private IProductService productService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IProductImageUrlService productImageUrlService;

    @Override
    public List<ProductInforModel> csvToProductInforModelList(MultipartFile file) {
        List<ProductInforModel> productInforModels = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");
                // check product name is exist
                if (productService.findProductEntityByProductName(values[0]) != null) {
                    continue;
                }
                ProductEntity productEntity = new ProductEntity();
                productEntity.setName(values[0]);
                productEntity.setPrice(Utils.checkRoundDirection(BigDecimal.valueOf(Double.parseDouble(values[1]))));
                productEntity.setQuantity(Integer.parseInt(values[2]));
                productEntity = productService.saveProductEntity(productEntity);
                Set<ProductImageurlEntity> productImageurlEntities = new HashSet<>();
//                if (values.length > 3) {
//                    List<String> list = Arrays.asList(values).subList(3, values.length);
//                    List<MultipartFile> multipartFiles = fileService.convertBase64ImageListToMultipartFileList(list);
//                    productImageurlEntities = productImageUrlService.saves(
//                            fileService.uploadMultipleFiles(multipartFiles, productEntity.getId()),
//                            productEntity.getId());
//
//                }
                List<String> images = fileService.getImageListByPathLists(productImageurlEntities.stream().map(ProductImageurlEntity::getImageUrl).toList());
                productInforModels.add(new ProductInforModel(productEntity, images));
            }
            return productInforModels;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productInforModels;
    }
}
