package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import com.beetech.trainningJava.enums.LogicalOperator;
import com.beetech.trainningJava.model.*;
import com.beetech.trainningJava.repository.ProductDiscountConditionRepository;
import com.beetech.trainningJava.service.IConditionService;
import com.beetech.trainningJava.service.IProductDiscountConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Lớp service thực hiện các chức năng liên quan đến product discount condition
 * @see IProductDiscountConditionService
 */
@Service
public class ProductDiscountConditionServiceImp implements IProductDiscountConditionService {
    @Autowired
    private ProductDiscountConditionRepository productDiscountConditionRepository;

    @Autowired
    private IConditionService conditionService;

    @Override
    public List<ProductDiscountConditionEntity> getProductDiscountConditionListByProductId(Integer productId) {
        return productDiscountConditionRepository.findAllByProductId(productId);
    }

    //region Lấy danh sách discount model thỏa mãn điều kiện theo danh sách cart product infor model
    @Override
    public List<DiscountModel> getDiscountModelListByCartProductInforModelList(List<CartProductInforModel> cartProductInforModelList) {// TODO SQL
        Map<Integer, DiscountModel> discountModelHashtable = new Hashtable<>();
        for (CartProductInforModel cartProductInforModel : cartProductInforModelList) {
            // lấy tất cả product discount condition theo product id
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList =
                    getProductDiscountConditionListByProductId(cartProductInforModel.getProduct().getId()); // TODO SQL

            // tạo ra danh sách discount model
            // đồng thời lọc ra những discount model thõa mãn với operator là OR
            discountModelHashtable = createDiscountModelMapByProductDiscountConditionEntityListAndCartProductInforModel(
                    productDiscountConditionEntityList,
                    cartProductInforModel,
                    discountModelHashtable);

        }

        // lọc ra những discount model thỏa mãn với operator là AND
        return filterDiscountModels(new ArrayList<>(discountModelHashtable.values()));
    }

    /**
     * Tạo và lọc ra những discount model có thể sử dụng được với logical operator là OR
     * @param productDiscountConditionEntityList danh sách product discount condition entity để taọ discount model
     * @param cartProductInforModel cart product infor model dùng để kiểm tra condition của sản phẩm có thõa mản điều kiện không
     * @return danh sách discount model có thể sử dụng được với logical operator là OR
     */
    Map<Integer, DiscountModel> createDiscountModelMapByProductDiscountConditionEntityListAndCartProductInforModel(
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList,
            CartProductInforModel cartProductInforModel,
            Map<Integer, DiscountModel> discountModelHashtable) {
        for (ProductDiscountConditionEntity productDiscountConditionEntity : productDiscountConditionEntityList) {
            // Kiểm tra xem discount model đã tồn tại trong danh sách discount model chưa
            if (discountModelHashtable.containsKey(productDiscountConditionEntity.getDiscount().getId())) {
                // lấy condition entity từ product discount condition entity
                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
                // tạo condition model từ cart product infor model và condition entity
                // và kiểm tra xem condition model có thỏa mãn điều kiện không
                ConditionModel conditionModel =
                        conditionService.getConditionModelByCartProductInforModelAndConditionEntity(cartProductInforModel, conditionEntity);//TODO SQL

                // lấy discount model từ danh sách discount model
                // và kiểm tra discount model có thỏa mãn điều kiện với logical operator là OR không
                DiscountModel discountModel = discountModelHashtable.get(productDiscountConditionEntity.getDiscount().getId());
                if (conditionModel.isEnoughCondition() && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                    discountModel.setAbleToUse(true);
                }

                // Thêm condition model vào discount model
                discountModel.getConditions().put(conditionModel.getId(), conditionModel);
            } else { // nếu discount model chưa tồn tại trong danh sách discount model
                // lấy condition entity từ product discount condition entity
                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
                // tạo condition model từ cart product infor model và condition entity
                // và kiểm tra xem condition model có thỏa mãn điều kiện không
                ConditionModel conditionModel =
                        conditionService.getConditionModelByCartProductInforModelAndConditionEntity(cartProductInforModel, conditionEntity);

                // tạo ra discount model mới với isAbleToUse = false
                // kiểm tra xem condition model có thỏa mãn điều kiện với logical operator là OR không
                Hashtable<String, ConditionModel> conditionModels = new Hashtable<>();
                DiscountModel discountModel = new DiscountModel(productDiscountConditionEntity.getDiscount(), conditionModels, false);
                if (conditionModel.isEnoughCondition() && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
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
     * @param discountModels danh sách discount model cần lọc
     * @return danh sách discount model có thể sử dụng được
     */
    List<DiscountModel> filterDiscountModels(List<DiscountModel> discountModels) {
        discountModels.forEach(discountModel -> { // TODO SQL
            // Lấy danh sách product discount condition từ discount id
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList =
                    productDiscountConditionRepository.findAllByDiscountId(discountModel.getId());

            // tạo danh sách các condition model theo danh sách product discount condition được tạo từ discount id
            List<ConditionModel> conditionModelList =
                    createConditionModelListByProductDiscountConditionEntityList(productDiscountConditionEntityList);

            conditionModelList.forEach(conditionModel -> {
                // thêm những condition model chưa có trong discount model
                if (!discountModel.getConditions().containsKey(conditionModel.getId())) { // CHECK CONTAIN
                    discountModel.getConditions().put(conditionModel.getId(), conditionModel);
                }
            });

            // Kiểm tra condition có thõa mãn với operator là AND
            if (isConditionEnoughWithLogicalOperatorIsAnd(discountModel.getConditions())) {
                // nếu có thõa mãn thì set isAbleToUse = true
                discountModel.setAbleToUse(true);
            }
        });
        return discountModels;
    }

    List<ConditionModel> createConditionModelListByProductDiscountConditionEntityList(
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList) {
        List<ConditionModel> conditionModelList = new ArrayList<>();
        productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
            // tạo condition model từ condition entity và product
            ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
            ConditionModel conditionModel = new ConditionModel(conditionEntity, false, productDiscountConditionEntity.getProduct());
            // thêm condition model vào danh sách
            conditionModelList.add(conditionModel);
        });
        // trả về danh sách condition model
        return conditionModelList;
    }

    boolean isConditionEnoughWithLogicalOperatorIsAnd(Map<String, ConditionModel> conditionModelList) {
        for (ConditionModel conditionModel : conditionModelList.values()) {
            if (!conditionModel.isEnoughCondition() && LogicalOperator.AND.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                return false;
            }
        }
        return true;
    }
    //endregion

    @Override
    public DiscountModel getDiscountModelByDiscountIdAndCartProductInforList(Integer discountId, List<CartProductInforModel> cartProductInforModels) {
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
}
