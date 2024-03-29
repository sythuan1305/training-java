package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.CategoryEntityDto;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductEntityDto;
import com.beetech.trainningJava.model.ProductInforModel;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Interface chứa các method xử lý logic của product
 *
 * @see com.beetech.trainningJava.service.imp.ProductServiceImp
 */
public interface IProductService {
    /**
     * Lưu thông tin product entity vào database
     *
     * @param productEntity là 1 entity cần lưu vào database
     * @return ProductEntity là thông tin của product sau khi lưu vào database
     */
    ProductEntity saveProductEntity(ProductEntity productEntity);

    /**
     * Tìm page model product infor model theo page index
     *
     * @param pageable tham số chứa các thông tin cần thiết để tìm kiếm
     * @return thông tin của page model product infor model sau khi tìm thấy
     */
    PageModel<ProductInforModel> findPageModelProductInforModelByPageIndex(Pageable pageable);

    /**
     * Tìm tất cả product entity dto theo tât cả category và page index
     * @param pageable tham số chứa các thông tin cần thiết để tìm kiếm
     * @return thông tin của page model product entity dto sau khi tìm thấy
     */
    List<CategoryEntityDto> findAllPageModelProductEntityDtoByAllCategoryAndPageable(
            List<CategoryEntity> categoryEntities,
            Pageable pageable);

    /**
     * Tìm product entity theo id
     *
     * @param id là id của product cần tìm
     * @return ProductEntity là thông tin của product sau khi tìm thấy
     */
    ProductEntity getProductEntityById(Integer id);

    /**
     * Tìm product infor model theo id
     *
     * @param id là id của product cần tìm
     * @return ProductInforModel là thông tin của product sau khi tìm thấy
     */
    ProductInforModel getProductInforModelById(Integer id);

    /**
     * Tìm product entity dto theo id
     *
     * @param id là id của product cần tìm
     * @return ProductEntityDto là thông tin của product sau khi tìm thấy
     */
    ProductEntityDto getProductEntityDtoById(Integer id);


    /**
     * Tạo product infor model từ product entity
     *
     * @param productEntity là product entity dùng để tạo product infor model
     * @return ProductInforModel là thông tin của product sau khi tạo
     */
    ProductInforModel createProductInforModelByProductEntity(ProductEntity productEntity);

    /**
     * Tìm product entity theo product name
     *
     * @param productName là tên của product cần tìm
     * @return ProductEntity là thông tin của product sau khi tìm thấy
     */
    ProductEntity findProductEntityByProductName(String productName);

    /**
     * Kiểm tra xem product entity có tồn tại trong database hay không
     *
     * @param productName là tên của product cần kiểm tra
     * @return true nếu tồn tại, false nếu không tồn tại
     */
    boolean isExistProductEntityByProductName(String productName);

    /**
     * Chuyển product entity thành product infor model
     *
     * @param productEntity là product entity cần chuyển
     * @return ProductInforModel là product infor model sau khi chuyển
     */
    ProductInforModel changeProductEntityToProductInforModel(ProductEntity productEntity);

    // for test
    void TestMinusQuantity(Integer number);
}
