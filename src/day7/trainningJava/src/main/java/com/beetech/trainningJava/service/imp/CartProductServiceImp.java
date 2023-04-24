package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.repository.CartProductRepository;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.service.ICartService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartProductServiceImp implements ICartProductService {
    @Autowired
    private ICartService cartService;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    @Override
    public CartProductEntity findCartProductEntityByCartProductEntity(CartProductEntity cartProductEntity) {
        return cartProductEntity;
    }

    @Override
    public List<CartProductEntity> getCartProductEntityListByCartIdAndIsBought(Integer cartId, boolean isBought) {
        return cartProductRepository.findAllByCartIdAndIsBought(cartId, isBought);
    }

    @Override
    public List<CartProductInforModel> changeCartProductInforListByCartProductEntityList(List<CartProductEntity> cartProductEntityList) {
        return cartProductEntityList.stream().map(
                        cartProductEntity -> new CartProductInforModel(
                                cartProductEntity,
                                productService.getProductInforModelById(cartProductEntity.getProduct().getId())))
                .toList();
    }

    @Override
    public CartProductEntity saveCartProductEntityWithAuthenticatedByCartProductParserList(Map<String, Object> cartProductParser) {
        // get product id, quantity, price from cart product parser
        int productId = Integer.parseInt(cartProductParser.get("product_id").toString());
        int quantity = Integer.parseInt(cartProductParser.get("quantity").toString());
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(cartProductParser.get("price").toString()));

        // check product exist and not bought in cart
        CartProductEntity cartProductEntity = cartProductRepository.findByCartIdAndProductIdAndIsBought(
                accountService.getCartEntity().getId(),
                productId,
                false);
        if (cartProductEntity != null) {
            cartProductEntity.setQuantity(cartProductEntity.getQuantity() + quantity);
            cartProductEntity.setPrice(cartProductEntity.getPrice().add(price));
            return cartProductRepository.save(cartProductEntity);
        }

        // create new cart product if product not exist in cart
        cartProductEntity = new CartProductEntity();
        ProductEntity productEntity = productService.getProductEntityById(productId);
        cartProductEntity.setCart(accountService.getCartEntity());
        cartProductEntity.setProduct(productEntity);
        cartProductEntity.setQuantity(quantity);
        cartProductEntity.setPrice(price);
        return cartProductRepository.save(cartProductEntity);
    }

    @Override
    public List<CartProductEntity> saveCartProductEntityListWithAuthenticatedByCartProductParserList(List<Map<String, Object>> cartProductParsers) {
        //Reference to instance method 'save' requires an enclosing instance of type 'CartProductServiceImpl'
        return cartProductParsers.stream().map(this::saveCartProductEntityWithAuthenticatedByCartProductParserList).toList();
    }

    @Override
    public List<CartProductInforModel> getCartProductInforListByCartIdAndIsBought(Integer cartId, boolean isBought) {
        List<CartProductEntity> cartProductEntities = getCartProductEntityListByCartIdAndIsBought(cartId, isBought);
        return changeCartProductInforListByCartProductEntityList(cartProductEntities);
    }

    @Override
    public List<CartProductInforModel> getCartProductInforListByCartProductParserList(List<Map<String, Object>> cartProductParserList) {
        return cartProductParserList.stream().map(this::getCartProductInforByCartProductParser).toList();
    }

    @Override
    public CartProductInforModel getCartProductInforByCartProductParser(Map<String, Object> cartProductParser) {
        // create new cart product from cart product parser
        CartProductEntity cartProductEntity = new CartProductEntity(
                (int) Long.parseLong(cartProductParser.get("id").toString()), // generate random id unique
                Integer.parseInt(cartProductParser.get("quantity").toString()),
                BigDecimal.valueOf(Double.parseDouble(cartProductParser.get("price").toString())),
                null, // null cart
                productService.getProductEntityById(Integer.parseInt(cartProductParser.get("product_id").toString())),
                null, // null order,
                false
        );
        // get product infor model by product id
        ProductInforModel productInforModel = productService.getProductInforModelById(cartProductEntity.getProduct().getId());
        // return new cart product infor model by cart product entity and product infor model
        return new CartProductInforModel(cartProductEntity, productInforModel);
    }

    @Override
    public List<CartProductInforModel> getCartProductInforModelListByCartProductInforModelListAndCartProductArray(
            List<CartProductInforModel> cartProductInforModelList,
            Integer[] cartProductIds) {
        // filter cart product infor model list by cart product id array
        // return new cart product infor model list
        return cartProductInforModelList.stream().filter(cartProductInforModel -> {
            for (Integer cartProductId : cartProductIds) {
                if (cartProductInforModel.getId().equals(cartProductId)) {
                    return true;
                }
            }
            return false;
        }).toList();
    }

    @Override
    public List<CartProductEntity> saveCartProductEntityListByCartProductModelList(List<CartProductInforModel> cartProductInforModelList) {
        return cartProductInforModelList.stream().map(cartProductInforModel -> {
            // create new cart product entity from cart product infor model
            cartProductInforModel.setId(null);
            CartProductEntity cartProductEntity = new CartProductEntity(cartProductInforModel);
            // save cart product entity with un auth
            return cartProductRepository.save(cartProductEntity);
        }).toList();
    }

    @Override
    public List<CartProductEntity> changeCartProductInforModelListToCartProductEntityList(List<CartProductInforModel> cartProductInforModelList) {
        return cartProductInforModelList.stream().map(cartProductInforModel -> {
            // create new cart product entity from cart product infor model
            CartProductEntity cartProductEntity = new CartProductEntity(cartProductInforModel);
            return cartProductEntity;
        }).toList();
    }

    @Override
    public List<CartProductInforModel> createCartProductInforListWithLoginOrNotWithCartProductParserList(
            List<Map<String, Object>> cartProductParserList) {
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = getCartProductInforListByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
        } else {
            cartProductInforModels = getCartProductInforListByCartProductParserList(cartProductParserList);
        }
        return cartProductInforModels;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void TestMinusQuantity() {
        // lấy đối tượng đơn hàng đã tồn tại với id = 32
        CartProductEntity cartProductEntity1 = cartProductRepository.getReferenceById(32);

        // print CartProductEntity1
        System.out.println("CartProductEntity1: " + cartProductEntity1.getQuantity());
        // trừ số lượng
        cartProductEntity1.setQuantity(cartProductEntity1.getQuantity() + 1000);
//        throw new RuntimeException("Test Exception");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void TestSave(boolean isException) {
        // khởi tạo đối tượng đơn hàng
        CartProductEntity cartProductEntity = new CartProductEntity();
        ProductEntity productEntity = productService.getProductEntityById(1);
        cartProductEntity.setCart(cartService.getCartEntityById(1));
        cartProductEntity.setProduct(productEntity);
        cartProductEntity.setQuantity(10);
        cartProductEntity.setPrice(BigDecimal.ONE);
//        if (isException) {
//            throw new RuntimeException("Test Exception");
//        }
        // lưu đối tượng đơn hàng vào database
//        cartProductRepository.save(cartProductEntity);
    }
}
