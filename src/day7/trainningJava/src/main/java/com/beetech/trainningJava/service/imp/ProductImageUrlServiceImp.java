package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.repository.ProductImageurlRepository;
import com.beetech.trainningJava.repository.ProductRepository;
import com.beetech.trainningJava.service.IProductImageUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductImageUrlServiceImp implements IProductImageUrlService {
    @Autowired
    ProductImageurlRepository productImageurlRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductImageurlEntity saveEntity(ProductImageurlEntity productImageurlEntity) {
        return productImageurlRepository.save(productImageurlEntity);
    }

    @Override
    public Set<ProductImageurlEntity> saveEntityList(List<String> imageUrls, Integer productId) {
        Set<ProductImageurlEntity> productImageurlEntities = new LinkedHashSet<>();
        for (String imageUrl : imageUrls) {
            ProductImageurlEntity productImageurlEntity = new ProductImageurlEntity();
            productImageurlEntity.setProduct(productRepository.getReferenceById(productId));
            productImageurlEntity.setImageUrl(imageUrl);
            productImageurlEntities.add(saveEntity(productImageurlEntity));
        }
        return productImageurlEntities;
    }

    @Override
    public List<ProductImageurlEntity> findEntityByProductId(Integer productId) {
        return productImageurlRepository.findAllByProductId(productId);
    }
}
