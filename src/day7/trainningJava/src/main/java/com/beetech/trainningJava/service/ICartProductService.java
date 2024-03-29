package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.model.CartProductEntityDto;
import com.beetech.trainningJava.model.CartProductInforModel;

import java.util.List;
import java.util.Map;

/**
 * Interface chứa các method xư lý logic của cart product
 *
 * @see com.beetech.trainningJava.service.imp.CartProductServiceImp
 */
public interface ICartProductService {
    /**
     * Lấy danh sách cart product entity theo cart id và is bought
     *
     * @param cartId   là id của cart dùng để tìm danh sách cart product entity
     * @param isBought là trạng thái mua dùng để tìm danh sách cart product entity
     * @return danh sách cart product entity tìm được theo cart id và is bought
     */
    List<CartProductEntity> getCartProductEntityListByCartIdAndIsBought(Integer cartId, boolean isBought);

    /**
     * Thay đổi danh sách cart product entity thành danh sách cart product infor model
     *
     * @param cartProductEntityList là danh sách cart product entity cần thay đổi
     * @return danh sách cart product infor model từ danh sách cart product entity
     */
    List<CartProductInforModel> changeCartProductInforListByCartProductEntityList(List<CartProductEntity> cartProductEntityList);


    /**
     * Lưu danh sách cart product entity vào database bằng danh sách cart product parser với user đã đăng nhập
     *
     * @param cartProductParsers là danh sách cart product parser (là danh sách được parse từ json) dùng để tạo danh sách cart product entity
     * @return Danh sách cart product entity sau khi lưu vào database
     */
    List<CartProductEntity> saveCartProductEntityListWithAuthenticatedByCartProductParserList(List<Map<String, Object>> cartProductParsers);

    /**
     * Lưu cart product entity vào database bằng danh sách cart product DTO với user đã đăng nhập
     *
     * @param cartProductEntityDto là cart product DTO dùng để tạo danh sách cart product entity
     * @return Cart product dto sau khi lưu vào database
     */
    CartProductEntityDto saveCartProductEntityWithAuthenticatedByCartProductEntityDto(CartProductEntityDto cartProductEntityDto);

    /**
     * Lưu danh sách cart product entity vào database bằng danh sách cart product parser với user đã đăng nhập
     *
     * @param cartProductParser là cart product parser (là đối tượng được parse từ json) dùng để tạo danh sách cart product entity
     * @return Danh sách cart product entity sau khi lưu vào database
     */
    CartProductEntity saveCartProductEntityWithAuthenticatedByCartProductParser(Map<String, Object> cartProductParser);

    /**
     * Lấy danh sách cart product infor model theo cart id và is bought
     *
     * @param cartId   là id của cart dùng để tìm danh sách cart product infor model
     * @param isBought là trạng thái mua dùng để tìm danh sách cart product infor model
     * @return danh sách cart product infor model tìm được theo cart id và is bought
     */
    List<CartProductInforModel> getCartProductInforListByCartIdAndIsBought(Integer cartId, boolean isBought);


    /**
     * Lấy danh sách cart product entity dto theo cart id và is bought
     *
     * @param cartId   là id của cart dùng để tìm danh sách cart product infor model
     * @param isBought là trạng thái mua dùng để tìm danh sách cart product infor model
     * @return danh sách cart product entity dto tìm được theo cart id và is bought
     */
    List<CartProductEntityDto> getCartProductEntityDtoListByCartIdAndIsBought(Integer cartId, boolean isBought);

    /**
     * Tạo danh sách cart product infor model bằng danh sách cart product parser
     *
     * @param cartProductParserList là danh sách cart product parser (là danh sách được parse từ json) dùng để tạo danh sách cart product infor model
     * @return danh sách cart product infor model tạo được từ danh sách cart product parser
     */
    List<CartProductInforModel> getCartProductInforListByCartProductParserList(List<Map<String, Object>> cartProductParserList);

    /**
     * Tạo danh sách cart product entity dto bằng danh sách cart product parser
     *
     * @param cartProductParserList là danh sách cart product parser (là danh sách được parse từ json) dùng để tạo danh sách cart product entity dto
     * @return danh sách cart product entity dto tạo được từ danh sách cart product parser
     */
    List<CartProductEntityDto> getCartProductEntityDtoListByCartProductParserList(List<Map<String, Object>> cartProductParserList);

    /**
     * Tạo cart product infor model bằng cart product parser
     *
     * @param cartProductParser là cart product parser (là đối tượng được parse từ json) dùng để tạo cart product infor model
     * @return cart product infor model tạo được từ cart product parser
     */
    CartProductInforModel getCartProductInforByCartProductParser(Map<String, Object> cartProductParser);

    /**
     * Tìm những cartProductInforModel có id trong mảng cartProducts
     *
     * @param cartProductInforModelList là danh sách cart product infor model dùng để tìm
     * @param cartProducts              là mảng id của cart product infor model cần tìm
     * @return danh sách cart product infor model tìm được
     */
    List<CartProductInforModel> getCartProductInforModelListByCartProductInforModelListAndCartProductArray(
            List<CartProductInforModel> cartProductInforModelList, Integer[] cartProducts);

    /**
     * Tìm những cartProductEntityDto có id trong mảng cartProducts
     *
     * @param cartProductEntityList là danh sách cart product entity dto dùng để tìm
     * @param cartProducts          là mảng id của cart product infor model cần tìm
     * @return danh sách cart product entity dto tìm được
     */
    List<CartProductEntityDto> getCartProductEntityDtoListByCartProductEntityDtoListAndCartProductArray(
            List<CartProductEntityDto> cartProductEntityList, Long[] cartProducts);

    /**
     * Lưu danh sách cart product entity vào database bằng danh sách cart product infor model
     *
     * @param cartProductInforModelList là danh sách cart product infor model dùng để tạo danh sách cart product entity
     * @return danh sách cart product entity sau khi lưu vào database
     */
    List<CartProductEntity> saveCartProductEntityListByCartProductModelList(List<CartProductInforModel> cartProductInforModelList);

    /**
     * Lưu danh sách cart product entity vào database bằng danh sách cart product entity dto
     *
     * @param cartProductEntityDtoList là danh sách cart product entity dto dùng để tạo danh sách cart product entity
     * @return danh sách cart product entity sau khi lưu vào database
     */
    List<CartProductEntity> saveCartProductEntityListByCartProductEntityDtoList(List<CartProductEntityDto> cartProductEntityDtoList);

    /**
     * Thay đổi danh sách cart product infor model thành danh sách cart product entity
     *
     * @param cartProductInforModelList là danh sách cart product infor model cần thay đổi
     * @return danh sách cart product entity từ danh sách cart product infor model
     */
    List<CartProductEntity> changeCartProductInforModelListToCartProductEntityList(List<CartProductInforModel> cartProductInforModelList);

    /**
     * Thay đổi danh sách cart product entity dto thành danh sách cart product entity
     *
     * @param cartProductEntityDtoList là danh sách cart product entity dto cần thay đổi
     * @return danh sách cart product entity từ danh sách cart product entity dto
     */
    List<CartProductEntity> changeCartProductEntityDtoListToCartProductEntityList(List<CartProductEntityDto> cartProductEntityDtoList);

    /**
     * Tao danh sach cart product infor model voi user da dang nhap hoac chua dang nhap
     *
     * @param cartProductParserList dùng để tạo danh sách cart product infor model với user chưa đăng nhập
     * @return danh sách cart product infor model tạo được từ danh sách cart product parser
     */
    List<CartProductInforModel> createCartProductInforListWithLoginOrNotWithCartProductParserList(List<Map<String, Object>> cartProductParserList);

    /**
     * Tạo danh sách cart product entity dto với user đã đăng nhập hoặc chưa đăng nhập
     *
     * @param cartProductParserList dùng để tạo danh sách cart product entity dto với user chưa đăng nhập
     * @return danh sách cart product entity dto tạo được từ danh sách cart product parser
     */
    List<CartProductEntityDto> createCartProductEntityDtoListWithLoginOrNotWithCartProductParserList(List<Map<String, Object>> cartProductParserList);

    //for test
    CartProductEntity findCartProductEntityByCartProductEntity(CartProductEntity cartProductEntity);

    void TestMinusQuantity();

    void TestSave(boolean isException);
}
