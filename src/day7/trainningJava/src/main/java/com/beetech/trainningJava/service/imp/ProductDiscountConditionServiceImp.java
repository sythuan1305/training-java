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

    @Override
    public List<DiscountModel> getDiscountModelListByCartProductInforModelList(List<CartProductInforModel> cartProductInforModelList) {
        Hashtable<Integer, DiscountModel> discountModelHashtable = new Hashtable<>();
        cartProductInforModelList.forEach(cartProductInforModel -> {
            // get all product discount condition by product id
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList =
                    getProductDiscountConditionListByProductId(cartProductInforModel.getProduct().getId());
            // for each product discount condition
            productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
                // check discount exist
                if (discountModelHashtable.containsKey(productDiscountConditionEntity.getDiscount().getId())) {
                    // get discount model
                    DiscountModel discountModel = discountModelHashtable.get(productDiscountConditionEntity.getDiscount().getId());
                    ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();

                    // get and check condition enough with logical operator is OR
                    ConditionModel conditionModel =
                            conditionService.getConditionModelByCartProductInforModelAndConditionEntity(cartProductInforModel, conditionEntity);
                    if (conditionModel.isEnoughCondition() && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                        discountModel.setAbleToUse(true);
                    }

                    // add condition to discount
                    discountModel.getConditions().put(conditionModel.getId(), conditionModel);
                } else {
                    // create new discount model with isAbleToUse = false
                    Hashtable<String, ConditionModel> conditionModels = new Hashtable<>();
                    DiscountModel discountModel = new DiscountModel(productDiscountConditionEntity.getDiscount(), conditionModels, false);

                    // create new condition model
                    ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();

                    // check condition enough with logical operator is OR
                    ConditionModel conditionModel =
                            conditionService.getConditionModelByCartProductInforModelAndConditionEntity(cartProductInforModel, conditionEntity);
                    if (conditionModel.isEnoughCondition() && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                        discountModel.setAbleToUse(true);
                    }
                    conditionModels.put(conditionModel.getId(), conditionModel);

                    // add condition to discount
                    discountModelHashtable.put(productDiscountConditionEntity.getDiscount().getId(), discountModel);
                }
            });
        });
        return filterDiscountModels(new ArrayList<>(discountModelHashtable.values()));
    }

    List<DiscountModel> filterDiscountModels(List<DiscountModel> discountModels) {
        discountModels.forEach(discountModel -> {
            // get all product discount condition by discount id
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList =
                    productDiscountConditionRepository.findAllByDiscountId(discountModel.getId());

            // tạo danh sách các condition model từ danh sách product discount condition
            List<ConditionModel> conditionModelList =
                    createConditionModelListByProductDiscountConditionEntityList(productDiscountConditionEntityList);

            conditionModelList.forEach(conditionModel -> {
                // thêm những condition model chưa có trong discount model
                if (!discountModel.getConditions().containsKey(conditionModel.getId())) {
                    discountModel.getConditions().put(conditionModel.getId(), conditionModel);
                }
            });

//            productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
//                // create new condition model with isEnoughCondition = false
//                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
//                ConditionModel conditionModel = new ConditionModel(conditionEntity, false, productDiscountConditionEntity.getProduct());
//                // thêm những condition model chưa có trong discount model
//                if (!discountModel.getConditions().containsKey(conditionModel.getId())) {
//                    discountModel.getConditions().put(conditionModel.getId(), conditionModel);
//                }
//            });

            // Kiểm tra điều kiện có thõa mãn với operator là AND
            if (isConditionEnoughWithLogicalOperatorIsAnd(discountModel.getConditions())) {
                discountModel.setAbleToUse(true);
            }
        });
        return discountModels;
    }

    List<ConditionModel> createConditionModelListByProductDiscountConditionEntityList(
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList) {
        List<ConditionModel> conditionModelList = new ArrayList<>();
        productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
            ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
            ConditionModel conditionModel = new ConditionModel(conditionEntity, false, productDiscountConditionEntity.getProduct());
            conditionModelList.add(conditionModel);
        });
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

    @Override
    public DiscountModel getDiscountModelByDiscountIdAndCartProductInforList(Integer discountId, List<CartProductInforModel> cartProductInforModels) {
        if (discountId == null) {
            return null;
        }
        List<DiscountModel> discountModels = getDiscountModelListByCartProductInforModelList(cartProductInforModels);
        for (DiscountModel discountModel : discountModels) {
            if (discountModel.getId().equals(discountId)) {
                return discountModel;
            }
        }
        return null;
    }
}
