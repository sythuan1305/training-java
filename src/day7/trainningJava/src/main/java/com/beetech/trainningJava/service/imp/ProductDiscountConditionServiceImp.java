package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.entity.DiscountEntity;
import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.enums.LogicalOperator;
import com.beetech.trainningJava.model.*;
import com.beetech.trainningJava.repository.ProductDiscountConditionRepository;
import com.beetech.trainningJava.service.IConditionService;
import com.beetech.trainningJava.service.IProductDiscountConditionService;
import com.beetech.trainningJava.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductDiscountConditionServiceImp implements IProductDiscountConditionService {
    @Autowired
    private ProductDiscountConditionRepository productDiscountConditionRepository;

    @Autowired
    private IDiscountService discountService;

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
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList = getProductDiscountConditionListByProductId(cartProductInforModel.getProduct().getId());
            // for each product discount condition
            productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {;
                // check discount exist
                if (discountModelHashtable.containsKey(productDiscountConditionEntity.getDiscount().getId())) {
                    DiscountModel discountModel = discountModelHashtable.get(productDiscountConditionEntity.getDiscount().getId());
                    ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();

                    // check condition enough
                    ConditionModel conditionModel = conditionService.getConditionModelByCartProductInforModelAndConditionEntity(cartProductInforModel, conditionEntity);
                    if (conditionModel.isEnoughCondition() && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                        discountModel.setAbleToUse(true);
                    }

                    // add condition to discount
                    discountModel.getConditions().put(conditionModel.getId(), conditionModel);
                } else {
                    // create new discount model
                    Hashtable<String, ConditionModel> conditionModels = new Hashtable<>();
                    DiscountModel discountModel = new DiscountModel(productDiscountConditionEntity.getDiscount(), conditionModels, false);

                    // create new condition model
                    ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();

                    // check condition
                    ConditionModel conditionModel = conditionService.getConditionModelByCartProductInforModelAndConditionEntity(cartProductInforModel, conditionEntity);
                    if (conditionModel.isEnoughCondition() && LogicalOperator.OR.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                        discountModel.setAbleToUse(true);
                    }
                    conditionModels.put(conditionModel.getId(), conditionModel);

                    // add condition to discount
                    discountModelHashtable.put(productDiscountConditionEntity.getDiscount().getId(), discountModel);
                }
            });
        });
        return FilterDiscountModels(new ArrayList<>(discountModelHashtable.values()));
    }

    @Override
    public DiscountModel getDiscountModelByCartProductInforList(Integer discountId, List<CartProductInforModel> cartProductInforModels) {
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

    List<DiscountModel> FilterDiscountModels(List<DiscountModel> discountModels) {
        discountModels.forEach(discountModel -> {
            // get all product discount condition by discount id
            List<ProductDiscountConditionEntity> productDiscountConditionEntityList = productDiscountConditionRepository.findAllByDiscountId(discountModel.getId());
            // for each product discount condition
            productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
                ConditionEntity conditionEntity = productDiscountConditionEntity.getCondition();
                ConditionModel conditionModel = new ConditionModel(conditionEntity, false, productDiscountConditionEntity.getProduct());
                if (!discountModel.getConditions().containsKey(conditionModel.getId())) {
                    discountModel.getConditions().put(conditionModel.getId(), conditionModel);
                }
            });

            // check condition enough
            if (isConditionEnoughWithLogicalOperatorIsAnd(discountModel.getConditions())) {
                discountModel.setAbleToUse(true);
            }
        });
        return discountModels;
    }

    boolean isConditionEnoughWithLogicalOperatorIsAnd(Hashtable<String, ConditionModel> conditionModelList) {
        for (ConditionModel conditionModel : conditionModelList.values()) {
            if (!conditionModel.isEnoughCondition() && LogicalOperator.AND.equals(conditionModel.getConditionEntity().getLogicalOperator())) {
                return false;
            }
        }
        return true;
    }
}
