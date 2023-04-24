package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;

import java.util.List;

/**
 * Interface chứa các method xử lý logic của product
 * @see com.beetech.trainningJava.service.imp.ProductServiceImp
 */
public interface IProductService {
    /**
     * Lưu thông tin product entity vào database
     * @param productEntity là 1 entity cần lưu vào database
     * @return ProductEntity là thông tin của product sau khi lưu vào database
     */
    ProductEntity saveProductEntity(ProductEntity productEntity);

    /**
     * Tìm page model product entity theo page index
     * @param pageIndex chỉ số trang
     * @param size số lượng product trên 1 trang
     * @param sort sắp xếp theo trường nào
     * @return thông tin của page model product entity sau khi tìm thấy
     */
    PageModel<ProductEntity> findPageModeProductEntitylByPageIndex(Integer pageIndex, Integer size, String sort);

    /**
     * Tìm page model product infor model theo page index
     * @param pageIndex chỉ số trang
     * @param size số lượng product trên 1 trang
     * @param sort sắp xếp theo trường nào
     * @return thông tin của page model product infor model sau khi tìm thấy
     */
    PageModel<ProductInforModel> findPageModelProductInforModelByPageIndex(Integer pageIndex, Integer size, String sort);

    /**
     * Tìm product entity theo id
     * @param id là id của product cần tìm
     * @return ProductEntity là thông tin của product sau khi tìm thấy
     */
    ProductEntity getProductEntityById(Integer id);

    /**
     * Tìm product infor model theo id
     * @param id là id của product cần tìm
     * @return ProductInforModel là thông tin của product sau khi tìm thấy
     */
    ProductInforModel getProductInforModelById(Integer id);

    /**
     * Tìm product entity theo product name
     * @param productName là tên của product cần tìm
     * @return ProductEntity là thông tin của product sau khi tìm thấy
     */
    ProductEntity findProductEntityByProductName(String productName);

    // for test
    void TestMinusQuantity(Integer number);
}
