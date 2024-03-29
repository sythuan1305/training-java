package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.model.ConditionEntityDto;
import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import com.beetech.trainningJava.enums.LogicalOperator;
import com.beetech.trainningJava.model.*;
import com.beetech.trainningJava.repository.ProductDiscountConditionRepository;
import com.beetech.trainningJava.service.IConditionService;
import com.beetech.trainningJava.service.IProductDiscountConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Lớp service thực hiện các chức năng liên quan đến product discount condition
 *
 * @see IProductDiscountConditionService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductDiscountConditionServiceImp implements IProductDiscountConditionService {
    private final ProductDiscountConditionRepository productDiscountConditionRepository;

    private final IConditionService conditionService;

    @Override
    public List<ProductDiscountConditionEntity> getProductDiscountConditionListByProductId(Integer productId) {
        return productDiscountConditionRepository.findAllByProductId(productId);
    }

    // region Lấy danh sách discount model thỏa mãn điều kiện theo danh sách cart
    // product infor model
    @Override
    public List<DiscountModel> getDiscountModelListByCartProductInforModelList(
            List<CartProductInforModel> cartProductInforModelList) {// TODO SQL
        Map<Integer, DiscountModel> discountModelHashtable = new Hashtable<>();
        Map<Integer, CartProductInforModel> cartProductInforModelHash = new Hashtable<>();

        // change list to map
        for (CartProductInforModel cartProductInforModel : cartProductInforModelList) {
            cartProductInforModelHash.put(cartProductInforModel.getProduct().getId(), cartProductInforModel);
        }

        // lấy tất cả product discount condition theo danh sách product id
        List<ProductDiscountConditionEntity> productDiscountConditionEntityList = productDiscountConditionRepository
                .findAllByProductIdIn(new ArrayList<>(cartProductInforModelHash.keySet()));

        // tạo ra danh sách discount model
        // đồng thời lọc ra những discount model thõa mãn với operator là OR
        discountModelHashtable = createDiscountModelMapByProductDiscountConditionEntityListAndCartProductInforModelHash(
                productDiscountConditionEntityList,
                cartProductInforModelHash,
                discountModelHashtable);

        // lọc ra những discount model thỏa mãn với operator là AND
        return filterDiscountModels(discountModelHashtable);
    }

    @Override
    public List<DiscountEntityDto> getDiscountEntityDtoListByCartProductInforModelList(
            List<CartProductEntityDto> cartProductEntityDtos) {
        Map<Integer, DiscountEntityDto> discountEntityDtoHashtable = new Hashtable<>();
        Map<Integer, CartProductEntityDto> cartProductEntityDtoHashtable = new Hashtable<>();

        // change list to map
        for (CartProductEntityDto cartProductEntityDto : cartProductEntityDtos) {
            cartProductEntityDtoHashtable.put(cartProductEntityDto.getProduct().getId(), cartProductEntityDto);
        }

        // lấy tất cả product discount condition theo danh sách product id
        List<ProductDiscountConditionEntity> productDiscountConditionEntityList = productDiscountConditionRepository
                .findAllByProductIdIn(new ArrayList<>(cartProductEntityDtoHashtable.keySet()));

        // tạo ra danh sách discount model
        // đồng thời lọc ra những discount model thõa mãn với operator là OR
        discountEntityDtoHashtable =
                createDiscountEntityDtoMapByProductDiscountConditionEntityListAndCartProductEntityDtoHashTable(
                        productDiscountConditionEntityList,
                        cartProductEntityDtoHashtable,
                        discountEntityDtoHashtable);

        // lọc ra những discount model thỏa mãn với operator là AND
        return filterDiscountEntityDtos(discountEntityDtoHashtable);
    }

    /**
     * Tạo và lọc ra những discount model có thể sử dụng được với logical operator
     * là OR
     *
     * @param productDiscountConditionEntityList danh sách product discount
     *                                           condition entity để taọ discount
     *                                           model
     * @param cartProductEntityDtoHashtable      danh sách cart product infor model
     *                                           dùng để kiểm tra condition của sản
     *                                           phẩm có thõa mản điều kiện không
     * @return danh sách discount model có thể sử dụng được với logical operator là
     * OR
     */
    Map<Integer, DiscountEntityDto> createDiscountEntityDtoMapByProductDiscountConditionEntityListAndCartProductEntityDtoHashTable(
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList,
            Map<Integer, CartProductEntityDto> cartProductEntityDtoHashtable,
            Map<Integer, DiscountEntityDto> discountEntityDtoHashtable) {
        for (ProductDiscountConditionEntity productDiscountConditionEntity : productDiscountConditionEntityList) {
            // Kiểm tra xem discount model đã tồn tại trong danh sách discount model chưa
            if (discountEntityDtoHashtable.containsKey(productDiscountConditionEntity.getDiscount().getId())) {
                // lấy condition entity từ product discount condition entity
                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
                // tạo condition model từ cart product infor model và condition entity
                // và kiểm tra xem condition entity dto có thỏa mãn điều kiện không
                ConditionEntityDto conditionEntityDto = conditionService
                        .getConditionEntityDtoByCartProductEntityDtoAndConditionEntity(
                                cartProductEntityDtoHashtable.get(productDiscountConditionEntity.getProduct().getId()),
                                conditionEntity); // TODO SQL
                // lấy discount entity dto từ danh sách discount model
                // và kiểm tra discount model có thỏa mãn điều kiện với logical operator là OR không
                DiscountEntityDto discountEntityDto = discountEntityDtoHashtable
                        .get(productDiscountConditionEntity.getDiscount().getId());
                if (conditionEntityDto.getIsEnoughCondition()
                        && LogicalOperator.OR.equals(conditionEntityDto.getLogicalOperator())) {
                    discountEntityDto.setAbleToUse(true);
                }

                // Thêm condition model vào discount model
                discountEntityDto.getConditions().put(conditionEntityDto.getId(), conditionEntityDto);
            } else { // nếu discount model chưa tồn tại trong danh sách discount model
                // lấy condition entity từ product discount condition entity
                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
                // tạo condition entity dto từ cart product infor model và condition entity
                // và kiểm tra xem condition model có thỏa mãn điều kiện không
                ConditionEntityDto conditionEntityDto = conditionService
                        .getConditionEntityDtoByCartProductEntityDtoAndConditionEntity(
                                cartProductEntityDtoHashtable.get(productDiscountConditionEntity.getProduct().getId()),
                                conditionEntity); // TODO SQL

                // tạo ra discount model mới với isAbleToUse = false
                // kiểm tra xem condition model có thỏa mãn điều kiện với logical operator là OR
                // không
                Hashtable<String, ConditionEntityDto> conditionEntityDtos = new Hashtable<>();
                DiscountEntityDto discountEntityDto = new DiscountEntityDto(productDiscountConditionEntity.getDiscount(),
                        conditionEntityDtos, false);
                if (conditionEntityDto.getIsEnoughCondition()
                        && LogicalOperator.OR.equals(conditionEntityDto.getLogicalOperator())) {
                    discountEntityDto.setAbleToUse(true);
                }

                // Thêm condition model vào discount model
                conditionEntityDtos.put(conditionEntityDto.getId(), conditionEntityDto);

                // thêm discount model vào danh sách discount model
                discountEntityDtoHashtable.put(productDiscountConditionEntity.getDiscount().getId(), discountEntityDto);
            }
        }
        return discountEntityDtoHashtable;
    }

    /**
     * Tạo và lọc ra những discount model có thể sử dụng được với logical operator
     * là OR
     *
     * @param productDiscountConditionEntityList danh sách product discount
     *                                           condition entity để taọ discount
     *                                           model
     * @param cartProductInforModelHash          danh sách cart product infor model
     *                                           dùng để kiểm tra condition của sản
     *                                           phẩm có thõa mản điều kiện không
     * @return danh sách discount model có thể sử dụng được với logical operator là
     * OR
     */
    Map<Integer, DiscountModel> createDiscountModelMapByProductDiscountConditionEntityListAndCartProductInforModelHash(
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList,
            Map<Integer, CartProductInforModel> cartProductInforModelHash,
            Map<Integer, DiscountModel> discountModelHashtable) {
        for (ProductDiscountConditionEntity productDiscountConditionEntity : productDiscountConditionEntityList) {
            // Kiểm tra xem discount model đã tồn tại trong danh sách discount model chưa
            if (discountModelHashtable.containsKey(productDiscountConditionEntity.getDiscount().getId())) {
                // lấy condition entity từ product discount condition entity
                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
                // tạo condition model từ cart product infor model và condition entity
                // và kiểm tra xem condition model có thỏa mãn điều kiện không
                ConditionModel conditionModel = conditionService
                        .getConditionModelByCartProductInforModelAndConditionEntity(
                                cartProductInforModelHash.get(productDiscountConditionEntity.getProduct().getId()),
                                conditionEntity); // TODO SQL
                // lấy discount model từ danh sách discount model
                // và kiểm tra discount model có thỏa mãn điều kiện với logical operator là OR
                // không
                DiscountModel discountModel = discountModelHashtable
                        .get(productDiscountConditionEntity.getDiscount().getId());
                if (conditionModel.isEnoughCondition()
                        && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                    discountModel.setAbleToUse(true);
                }

                // Thêm condition model vào discount model
                discountModel.getConditions().put(conditionModel.getId(), conditionModel);
            } else { // nếu discount model chưa tồn tại trong danh sách discount model
                // lấy condition entity từ product discount condition entity
                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
                // tạo condition model từ cart product infor model và condition entity
                // và kiểm tra xem condition model có thỏa mãn điều kiện không
                ConditionModel conditionModel = conditionService
                        .getConditionModelByCartProductInforModelAndConditionEntity(
                                cartProductInforModelHash.get(productDiscountConditionEntity.getProduct().getId()),
                                conditionEntity); // TODO SQL

                // tạo ra discount model mới với isAbleToUse = false
                // kiểm tra xem condition model có thỏa mãn điều kiện với logical operator là OR
                // không
                Hashtable<String, ConditionModel> conditionModels = new Hashtable<>();
                DiscountModel discountModel = new DiscountModel(productDiscountConditionEntity.getDiscount(),
                        conditionModels, false);
                if (conditionModel.isEnoughCondition()
                        && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                    discountModel.setAbleToUse(true);
                }

                // Thêm condition model vào discount model
                conditionModels.put(conditionModel.getId(), conditionModel);

                // thêm discount model vào danh sách discount model
                discountModelHashtable.put(productDiscountConditionEntity.getDiscount().getId(), discountModel);
            }
        }
        return discountModelHashtable;
    }

    /**
     * Lọc ra những discount model có thể sử dụng được <br>
     * Và kiểm tra những điều kiện có thõa mãn với operator là AND
     *
     * @param discountEntityDtos danh sách discount entity dto cần lọc
     * @return danh sách discount entity dto có thể sử dụng được
     */
    List<DiscountEntityDto> filterDiscountEntityDtos(Map<Integer, DiscountEntityDto> discountEntityDtos) {
        // Lấy danh sách product discount condition theo discount id từ danh sách
        // discount id
        Map<Integer, List<ProductDiscountConditionEntity>> productDiscountConditionEntityMap = productDiscountConditionRepository
                .findAllByDiscountIdInMap(new ArrayList<>(discountEntityDtos.keySet()));

        productDiscountConditionEntityMap.forEach((discountId, productDiscountConditionEntityList) -> {
            // tạo danh sách các condition model theo danh sách product discount condition
            // được tạo từ discount id
            List<ConditionEntityDto> conditionEntityDtoList = createConditionEntityDtoListByProductDiscountConditionEntityList(
                    productDiscountConditionEntityList);
            DiscountEntityDto discountEntityDto = discountEntityDtos.get(discountId);
            conditionEntityDtoList.forEach(conditionModel -> {
                // thêm những condition model chưa có trong discount model
                if (!discountEntityDto.getConditions().containsKey(conditionModel.getId())) { // CHECK CONTAIN
                    discountEntityDto.getConditions().put(conditionModel.getId(), conditionModel);
                }
            });

            // Kiểm tra condition có thõa mãn với operator là AND
            if (isConditionDtoEnoughWithLogicalOperatorIsAnd(discountEntityDtos.get(discountId).getConditions())) {
                // nếu có thõa mãn thì set isAbleToUse = true
                discountEntityDto.setAbleToUse(true);
            }
        });
        return new ArrayList<>(discountEntityDtos.values());
    }


    List<ConditionEntityDto> createConditionEntityDtoListByProductDiscountConditionEntityList(
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList) {
        List<ConditionEntityDto> conditionEntityDtos = new ArrayList<>();
        productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
            // tạo condition model từ condition entity và product
            ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
            ConditionEntityDto conditionEntityDto = new ConditionEntityDto(conditionEntity, false,
                    productDiscountConditionEntity.getProduct().getName());
            // thêm condition model vào danh sách
            conditionEntityDtos.add(conditionEntityDto);
        });
        // trả về danh sách condition model
        return conditionEntityDtos;
    }

    boolean isConditionDtoEnoughWithLogicalOperatorIsAnd(Map<String, ConditionEntityDto> conditionEntityDtoMap) {
        for (ConditionEntityDto conditionEntityDto : conditionEntityDtoMap.values()) {
            if (!conditionEntityDto.getIsEnoughCondition()
                    && LogicalOperator.AND.equals(conditionEntityDto.getLogicalOperator())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lọc ra những discount model có thể sử dụng được <br>
     * Và kiểm tra những điều kiện có thõa mãn với operator là AND
     *
     * @param discountModels danh sách discount model cần lọc
     * @return danh sách discount model có thể sử dụng được
     */
    List<DiscountModel> filterDiscountModels(Map<Integer, DiscountModel> discountModels) {
        // Lấy danh sách product discount condition theo discount id từ danh sách
        // discount id
        Map<Integer, List<ProductDiscountConditionEntity>> productDiscountConditionEntityMap = productDiscountConditionRepository
                .findAllByDiscountIdInMap(new ArrayList<>(discountModels.keySet()));

        productDiscountConditionEntityMap.forEach((discountId, productDiscountConditionEntityList) -> {
            // tạo danh sách các condition model theo danh sách product discount condition
            // được tạo từ discount id
            List<ConditionModel> conditionModelList = createConditionModelListByProductDiscountConditionEntityList(
                    productDiscountConditionEntityList);
            DiscountModel discountModel = discountModels.get(discountId);
            conditionModelList.forEach(conditionModel -> {
                // thêm những condition model chưa có trong discount model
                if (!discountModel.getConditions().containsKey(conditionModel.getId())) { // CHECK CONTAIN
                    discountModel.getConditions().put(conditionModel.getId(), conditionModel);
                }
            });

            // Kiểm tra condition có thõa mãn với operator là AND
            if (isConditionEnoughWithLogicalOperatorIsAnd(discountModels.get(discountId).getConditions())) {
                // nếu có thõa mãn thì set isAbleToUse = true
                discountModel.setAbleToUse(true);
            }
        });
        return new ArrayList<>(discountModels.values());
    }

    List<ConditionModel> createConditionModelListByProductDiscountConditionEntityList(
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList) {
        List<ConditionModel> conditionModelList = new ArrayList<>();
        productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
            // tạo condition model từ condition entity và product
            ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
            ConditionModel conditionModel = new ConditionModel(conditionEntity, false,
                    productDiscountConditionEntity.getProduct());
            // thêm condition model vào danh sách
            conditionModelList.add(conditionModel);
        });
        // trả về danh sách condition model
        return conditionModelList;
    }

    boolean isConditionEnoughWithLogicalOperatorIsAnd(Map<String, ConditionModel> conditionModelList) {
        for (ConditionModel conditionModel : conditionModelList.values()) {
            if (!conditionModel.isEnoughCondition()
                    && LogicalOperator.AND.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                return false;
            }
        }
        return true;
    }
    // endregion

    @Override
    public DiscountModel getDiscountModelByDiscountIdAndCartProductInforList(Integer discountId,
                                                                             List<CartProductInforModel> cartProductInforModels) {
        if (discountId == null) {
            return null;
        }
        // lấy danh sách discount model thoa mãn với danh sách cart product infor model
        List<DiscountModel> discountModels = getDiscountModelListByCartProductInforModelList(cartProductInforModels);
        // loc ra discount model có id trùng với discount id truyền vào
        for (DiscountModel discountModel : discountModels) {
            if (discountModel.getId().equals(discountId)) {
                return discountModel;
            }
        }
        return null;
    }

    @Override
    public DiscountEntityDto getDiscountEntityDtoByDiscountIdAndCartProductEntityDtoList(Integer discountId, List<CartProductEntityDto> cartProductEntityDtos) {
        if (discountId == null) {
            return null;
        }
        // lấy danh sách discount model thoa mãn với danh sách cart product infor model
        List<DiscountEntityDto> discountEntityDtos = getDiscountEntityDtoListByCartProductInforModelList(cartProductEntityDtos);
        // loc ra discount model có id trùng với discount id truyền vào
        for (DiscountEntityDto discountEntityDto : discountEntityDtos) {
            if (discountEntityDto.getId().equals(discountId)) {
                return discountEntityDto;
            }
        }
        return null;
    }
}
