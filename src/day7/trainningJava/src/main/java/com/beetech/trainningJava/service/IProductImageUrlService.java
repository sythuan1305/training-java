package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductImageurlEntity;

import java.util.List;
import java.util.Set;

public interface IProductImageUrlService {
    public ProductImageurlEntity saveEntity(ProductImageurlEntity productImageurlEntity);

    public Set<ProductImageurlEntity> saveEntityList(List<String> productImageurls, Integer productId);

    public List<ProductImageurlEntity> findEntityByProductId(Integer productId);
}
