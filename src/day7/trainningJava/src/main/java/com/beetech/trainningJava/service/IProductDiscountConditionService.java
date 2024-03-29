package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import com.beetech.trainningJava.model.CartProductEntityDto;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountEntityDto;
import com.beetech.trainningJava.model.DiscountModel;

import java.util.List;

/**
 * Interface chứa các method xư lý logic của product discount condition hoặc discount
 * @see com.beetech.trainningJava.service.imp.ProductDiscountConditionServiceImp
 */
public interface IProductDiscountConditionService {
    /**
     * Lấy danh sách product discount condition model theo product id
     * @param productId là id của product để lấy danh sách product discount condition model
     * @return danh sách product discount condition model
     */
    List<ProductDiscountConditionEntity> getProductDiscountConditionListByProductId(Integer productId);

    /**
     * Lấy danh sách discount model thõa mãn điều kiện theo danh sách cart product infor model
     * @param cartProductInforModels là danh sách cart product infor model để lấy danh sách discount model
     * @return danh sách discount model thõa mãn điều kiện theo danh sách cart product infor model
     */
    List<DiscountModel> getDiscountModelListByCartProductInforModelList(List<CartProductInforModel> cartProductInforModels);

    /**
     * Lấy danh sách discount entity dto thõa mãn điều kiện theo danh sách cart product entity dto
     * @param cartProductEntityDtos là danh sách cart product entity dto để lấy danh sách discount model
     * @return danh sách discount entity dto thõa mãn điều kiện theo danh sách cart product entity dto
     */
    List<DiscountEntityDto> getDiscountEntityDtoListByCartProductInforModelList(List<CartProductEntityDto> cartProductEntityDtos);

    /**
     * Lấy discount model mà user đã chọn thõa mãn điều kiện đơn hàng
     * @param discountId là id của discount model mà user đã chọn
     * @param cartProductInforModels là danh sách cart product infor model để kiểm tra discount model mà user đã chọn có thỏa mãn điều kiện hay không
     * @return discount model mà user đã chọn thõa mãn điều kiện
     */
    DiscountModel getDiscountModelByDiscountIdAndCartProductInforList(Integer discountId, List<CartProductInforModel> cartProductInforModels);

    /**
     * Lấy discount entity dto mà user đã chọn thõa mãn điều kiện đơn hàng
     * @param discountId là id của discount entity dto mà user đã chọn
     * @param cartProductEntityDtos là danh sách cart product entity dto để kiểm tra discount entity dto mà user đã chọn có thỏa mãn điều kiện hay không
     * @return discount entity dto mà user đã chọn thõa mãn điều kiện
     */
    DiscountEntityDto getDiscountEntityDtoByDiscountIdAndCartProductEntityDtoList(Integer discountId, List<CartProductEntityDto> cartProductEntityDtos);
}
