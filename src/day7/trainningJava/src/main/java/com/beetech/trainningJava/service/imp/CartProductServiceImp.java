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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class này dùng để triển khai các phương thức của interface
 * ICartProductService
 * 
 * @see ICartProductService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class CartProductServiceImp implements ICartProductService {
    private final ICartService cartService;

    private final CartProductRepository cartProductRepository;

    private final IProductService productService;

    private final IAccountService accountService;

    @Override
    public CartProductEntity findCartProductEntityByCartProductEntity(CartProductEntity cartProductEntity) {
        return cartProductEntity;
    }

    @Override
    public List<CartProductEntity> getCartProductEntityListByCartIdAndIsBought(Integer cartId, boolean isBought) {
        return cartProductRepository.findAllByCartIdAndIsBought(cartId, isBought);
    }

    @Override
    public List<CartProductInforModel> changeCartProductInforListByCartProductEntityList(
            List<CartProductEntity> cartProductEntityList) {
        return cartProductEntityList.stream().map(
                cartProductEntity -> new CartProductInforModel(
                        cartProductEntity,
                        productService.createProductInforModelByProductEntity(cartProductEntity.getProduct())))
                .toList(); // TODO SQL
    }

    @Override
    public CartProductEntity saveCartProductEntityWithAuthenticatedByCartProductParser(
            Map<String, Object> cartProductParser) {
        // khai báo các thông tin về sản phẩm từ cartProductParser
        int productId = Integer.parseInt(cartProductParser.get("product_id").toString());
        int quantity = Integer.parseInt(cartProductParser.get("quantity").toString());
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(cartProductParser.get("price").toString()));

        // kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
        CartProductEntity cartProductEntity = cartProductRepository.findByCartIdAndProductIdAndIsBought(
                accountService.getCartEntity().getId(),
                productId,
                false);

        // nếu sản phẩm đã tồn tại trong giỏ hàng thì cập nhật số lượng và giá
        if (cartProductEntity != null) {
            cartProductEntity.setQuantity(cartProductEntity.getQuantity() + quantity);
            cartProductEntity.setPrice(cartProductEntity.getPrice().add(price));
            return cartProductRepository.save(cartProductEntity);
        }

        // nếu sản phẩm chưa tồn tại trong giỏ hàng thì thêm mới
        cartProductEntity = new CartProductEntity();
        ProductEntity productEntity = productService.getProductEntityById(productId);
        cartProductEntity.setCart(accountService.getCartEntity());
        cartProductEntity.setProduct(productEntity);
        cartProductEntity.setQuantity(quantity);
        cartProductEntity.setPrice(price);
        return cartProductRepository.save(cartProductEntity);
    }

    @Override
    public List<CartProductEntity> saveCartProductEntityListWithAuthenticatedByCartProductParserList(
            List<Map<String, Object>> cartProductParsers) { // TODO SQL
        return cartProductParsers.stream().map(this::saveCartProductEntityWithAuthenticatedByCartProductParser)
                .toList();
    }

    @Override
    public List<CartProductInforModel> getCartProductInforListByCartIdAndIsBought(Integer cartId, boolean isBought) {
        List<CartProductEntity> cartProductEntities = getCartProductEntityListByCartIdAndIsBought(cartId, isBought);
        return changeCartProductInforListByCartProductEntityList(cartProductEntities);
    }

    @Override
    public List<CartProductInforModel> getCartProductInforListByCartProductParserList(
            List<Map<String, Object>> cartProductParserList) {
        return cartProductParserList.stream().map(this::getCartProductInforByCartProductParser).toList(); // TODO SQL
    }

    @Override
    public CartProductInforModel getCartProductInforByCartProductParser(Map<String, Object> cartProductParser) {
        // tạo cart product entity từ cart product parser
        CartProductEntity cartProductEntity = new CartProductEntity(
                (int) Long.parseLong(cartProductParser.get("id").toString()), // generate random id unique
                Integer.parseInt(cartProductParser.get("quantity").toString()),
                BigDecimal.valueOf(Double.parseDouble(cartProductParser.get("price").toString())),
                null, // null cart
                productService.getProductEntityById(Integer.parseInt(cartProductParser.get("product_id").toString())),
                false);
        // lấy product infor model từ product id
        ProductInforModel productInforModel = productService
                .getProductInforModelById(cartProductEntity.getProduct().getId());
        // trả về cart product infor model từ cart product entity và product infor model
        return new CartProductInforModel(cartProductEntity, productInforModel);
    }

    @Override
    public List<CartProductInforModel> getCartProductInforModelListByCartProductInforModelListAndCartProductArray(
            List<CartProductInforModel> cartProductInforModelList,
            Integer[] cartProductIds) {
        // lọc cart product infor model list theo cart product id array
        // trả về danh sách cart product infor model có id trong cart product id array
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
    public List<CartProductEntity> saveCartProductEntityListByCartProductModelList(
            List<CartProductInforModel> cartProductInforModelList) {
        return cartProductInforModelList.stream().map(cartProductInforModel -> {
            // tạo cart product entity mới từ cart product infor model
            cartProductInforModel.setId(null); // vì lưu vào database nên id phải null
            CartProductEntity cartProductEntity = new CartProductEntity(cartProductInforModel);
            // lưu cart product entity vào database
            return cartProductRepository.save(cartProductEntity);
        }).toList(); // TODO SQL
    }

    @Override
    public List<CartProductEntity> changeCartProductInforModelListToCartProductEntityList(
            List<CartProductInforModel> cartProductInforModelList) {
        // tạo list cart product entity từ list cart product infor model
        return cartProductInforModelList.stream().map(CartProductEntity::new).toList();
    }

    @Override
    public List<CartProductInforModel> createCartProductInforListWithLoginOrNotWithCartProductParserList(
            List<Map<String, Object>> cartProductParserList) {
        List<CartProductInforModel> cartProductInforModels;
        // nếu đã đăng nhập thì lấy danh sách cart product infor model từ cart id
        if (accountService.isLogin()) {
            cartProductInforModels = getCartProductInforListByCartIdAndIsBought(accountService.getCartEntity().getId(),
                    false);
        } else {
            // nếu chưa đăng nhập thì lấy danh sách cart product infor model từ cart
            // product parser list
            // nếu cart product parser list là null thì trả về list rỗng
            if (cartProductParserList == null) {
                return new ArrayList<>();
            }
            // nếu cart product parser list không phải null thì tạo danh sách cart
            // product infor model từ cart product parser list
            cartProductInforModels = getCartProductInforListByCartProductParserList(cartProductParserList);
        }
        return cartProductInforModels;
    }

    // region - test
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void TestMinusQuantity() {
        // lấy đối tượng đơn hàng đã tồn tại với id = 32
        CartProductEntity cartProductEntity1 = cartProductRepository.getReferenceById(32);

        // print CartProductEntity1
        System.out.println("CartProductEntity1: " + cartProductEntity1.getQuantity());
        // trừ số lượng
        cartProductEntity1.setQuantity(cartProductEntity1.getQuantity() + 1000);
        // throw new RuntimeException("Test Exception");
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
        // if (isException) {
        // throw new RuntimeException("Test Exception");
        // }
        // lưu đối tượng đơn hàng vào database
        // cartProductRepository.save(cartProductEntity);
    }
    // endregion
}
