package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductImageurlEntity;

import java.util.List;
import java.util.Set;

public interface IProductImageUrlService {
    public ProductImageurlEntity save(ProductImageurlEntity productImageurlEntity);

    public Set<ProductImageurlEntity> saves(List<String> productImageurls, Integer productId);

    public List<ProductImageurlEntity> findByProductId(Integer productId);
}
