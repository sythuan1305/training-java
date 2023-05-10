package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.aspect.annotation.LogMemoryAndCpu;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.repository.ProductImageurlRepository;
import com.beetech.trainningJava.repository.ProductRepository;
import com.beetech.trainningJava.service.IProductImageUrlService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class này dùng để triển khai các phương thức của interface IProductService
 *
 * @see IProductService
 */
@Service
@LogMemoryAndCpu
public class ProductServiceImp implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductImageUrlService productImageUrlService;

    @Autowired
    private ProductImageurlRepository productImageurlRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductEntity saveProductEntity(ProductEntity productEntity) {
        if (productEntity == null)
            throw new RuntimeException("productEntity is null");
        if (isExistProductEntityByProductName(productEntity.getName())) // CHECK EXISTS
            throw new RuntimeException("productEntity is exist");
        return productRepository.save(productEntity);
    }

    @Override
    public PageModel<ProductInforModel> findPageModelProductInforModelByPageIndex(Integer pageIndex, Integer size, String sort) {// TODO SQL
        // lấy page product entity theo page index, size, sort
        PageRequest pageRequest = PageRequest.of(pageIndex < 0 ? 0 : pageIndex, size, Sort.by(sort));
        Page<ProductEntity> paging = productRepository.findAll(pageRequest);

        // tạo list product infor model
        List<ProductInforModel> productInforModels = new ArrayList<>();

        for (ProductEntity productEntity : paging.getContent()) {
            // tạo product infor model từ product entity
            ProductInforModel productInforModel = changeProductEntityToProductInforModel(productEntity);
            // thêm product infor model vào list product infor model
            productInforModels.add(productInforModel);
        }
        double totalPage = (double) productRepository.count() / (double) size;
        return new PageModel<>(productInforModels, pageRequest.getPageNumber(), (long) Math.ceil(totalPage));
    }

    @Override
    public ProductEntity getProductEntityById(Integer productId) {
        if (productId == null)
            throw new RuntimeException("productId is null");
        return productRepository.getReferenceById(productId);
    }

    @Override
    public ProductEntity findProductEntityByProductName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public boolean isExistProductEntityByProductName(String productName) {
        return productRepository.existsByName(productName);
    }

    // region create product infor model
    @Override
    public ProductInforModel getProductInforModelById(Integer id) { // TODO SQL
        ProductEntity productEntity = getProductEntityById(id);
        List<String> images = productEntity
                .getProductImageurlEntities()
                .stream().map(productImageurlEntity ->
                        Utils.Base64Image.getImageByPath(productImageurlEntity.getImageUrl())).toList();
        return new ProductInforModel(productEntity, images);
    }

    @Override
    public ProductInforModel createProductInforModelByProductEntity(ProductEntity productEntity) {
        if (productEntity == null)
            throw new RuntimeException("productEntity is null");
        List<String> images = Utils.Base64Image.getImageListByPathLists(
                productEntity.getProductImageurlEntities().parallelStream().map(ProductImageurlEntity::getImageUrl).toList());
        return new ProductInforModel(productEntity, images);
    }

    @Override
    public ProductInforModel changeProductEntityToProductInforModel(ProductEntity productEntity) {
        // lấy list image url entity theo product id
        Set<ProductImageurlEntity> productImageurlEntities = productEntity.getProductImageurlEntities();
        // tạo list ảnh dạng base64

        List<String> images = Utils.Base64Image.getImageListByPathLists(productImageurlEntities.
                stream()
                .map(ProductImageurlEntity::getImageUrl)
                .toList());
        return new ProductInforModel(productEntity, images);
    }
    // endregion

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void TestMinusQuantity(Integer number) {
        ProductEntity productEntity = productRepository.getReferenceById(1);
        productEntity.setQuantity(productEntity.getQuantity() - number);
    }
}
