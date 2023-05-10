package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.ICSVService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Class này dùng để triển khai các phương thức của interface ICSVService
 * 
 * @see ICSVService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class CSVServiceImp implements ICSVService {
    private final IProductService productService;

    @Override
    public List<ProductInforModel> csvToProductInforModelList(MultipartFile file) {
        List<ProductInforModel> productInforModels = new ArrayList<>();
        // CLOSE RESOURCE
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) { // tự động close
            String line;
            // đọc từng dòng
            while ((line = br.readLine()) != null) {
                // đọc từng cột
                String[] values = line.split(",");

                // Nếu tên sản phẩm đã tồn tại thì bỏ qua
                if (productService.isExistProductEntityByProductName(values[0])) { // CHECK EXISTS
                    continue;
                }

                // lưu product entity vào db
                ProductEntity productEntity = new ProductEntity();
                productEntity.setName(values[0]);
                productEntity.setPrice(Utils.checkRoundDirection(BigDecimal.valueOf(Double.parseDouble(values[1]))));
                productEntity.setQuantity(Integer.parseInt(values[2]));
                productEntity = productService.saveProductEntity(productEntity);

                // lưu product image url entity vào db (nếu có)
                Set<ProductImageurlEntity> productImageurlEntities = new HashSet<>();
                List<String> images = Utils.Base64Image.getImageListByPathLists(
                        productImageurlEntities.stream().map(ProductImageurlEntity::getImageUrl).toList());
                // tạo product infor model từ product entity và list image
                // và thêm vào list product infor model
                productInforModels.add(new ProductInforModel(productEntity, images));
            }
            return productInforModels;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productInforModels;
    }
}
