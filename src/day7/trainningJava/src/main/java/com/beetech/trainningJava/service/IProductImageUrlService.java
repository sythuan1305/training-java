package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductImageurlEntity;

import java.util.List;
import java.util.Set;

/**
 * Interface chứa các method xư lý logic của product image url
 * @see com.beetech.trainningJava.service.imp.ProductImageUrlServiceImp
 */
public interface IProductImageUrlService {
    /**
     * Lưu thông tin product image url entity vào database
     * @param productImageurlEntity là 1 entity cần lưu vào database
     * @return ProductImageurlEntity là thông tin của product image url sau khi lưu vào database
     */
    public ProductImageurlEntity saveEntity(ProductImageurlEntity productImageurlEntity);

    /**
     * Lưu danh sách product image url entity vào database
     * @param productImageurls là danh sách product image url cần lưu vào database
     * @param productId là id của product
     * @return Set<ProductImageurlEntity> là danh sách product image url sau khi lưu vào database
     */
    public Set<ProductImageurlEntity> saveEntityList(List<String> productImageurls, Integer productId);

    /**
     * Tìm product image url entity theo product id
     * @param productId là id của product cần tìm
     * @return List<ProductImageurlEntity> là danh sách product image url sau khi tìm thấy
     */
    public List<ProductImageurlEntity> findEntityByProductId(Integer productId);
}
