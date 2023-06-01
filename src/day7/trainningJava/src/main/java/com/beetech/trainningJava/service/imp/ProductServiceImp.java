package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.aspect.annotation.LogMemoryAndCpu;
import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.*;
import com.beetech.trainningJava.model.mappper.ProductEntityDtoMapper;
import com.beetech.trainningJava.repository.CategoryRepository;
import com.beetech.trainningJava.repository.ProductImageurlRepository;
import com.beetech.trainningJava.repository.ProductRepository;
import com.beetech.trainningJava.service.ICategoryService;
import com.beetech.trainningJava.service.IProductImageUrlService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class này dùng để triển khai các phương thức của interface IProductService
 *
 * @see IProductService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@LogMemoryAndCpu
public class ProductServiceImp implements IProductService {
    private final ProductRepository productRepository;

    private final ProductEntityDtoMapper productEntityDtoMapper;

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
    public PageModel<ProductInforModel> findPageModelProductInforModelByPageIndex(Pageable pageable) {// TODO SQL
        Page<ProductEntity> paging = productRepository.findAll(pageable);

        // tạo list product infor model
        List<ProductInforModel> productInforModels = new ArrayList<>();

        for (ProductEntity productEntity : paging.getContent()) {
            // tạo product infor model từ product entity
            ProductInforModel productInforModel = changeProductEntityToProductInforModel(productEntity);
            // thêm product infor model vào list product infor model
            productInforModels.add(productInforModel);
        }
        double totalPage = (double) productRepository.count() / (double) paging.getContent().size();
        return new PageModel<>(productInforModels, pageable.getPageNumber(), (long) Math.ceil(totalPage));
    }

    @Override
    public List<CategoryEntityDto> findAllPageModelProductEntityDtoByAllCategoryAndPageable(
            List<CategoryEntity> categoryEntities,
            Pageable pageable) {
        List<CategoryEntityDto> categoryEntityDtos = new ArrayList<>();
        categoryEntities.forEach(categoryEntity -> {
            Set<ProductEntityDto> productEntityDtos =productRepository
                    .findAllByCategoryId(categoryEntity.getId(), pageable)
                    .stream()
                    .map(productEntityDtoMapper)
                    .collect(Collectors.toSet());
            categoryEntityDtos.add(new CategoryEntityDto(
                    categoryEntity.getId(),
                    categoryEntity.getName(),
                    productEntityDtos));
        });
        return categoryEntityDtos;
    }


    @Override
    public ProductEntity getProductEntityById(Integer productId) {
        if (productId == null)
            throw new RuntimeException("productId is null");
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        return productEntity.orElse(null);
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
        if (productEntity == null)
            throw new RuntimeException("productEntity is null");
        List<String> images = productEntity
                .getProductImageurlEntities()
                .stream()
                .map(productImageurlEntity -> Utils.Base64Image.getImageByPath(productImageurlEntity.getImageUrl()))
                .toList();
        return new ProductInforModel(productEntity, images);
    }

    @Override
    public ProductEntityDto getProductEntityDtoById(Integer id) {
        return productRepository.findById(id)
                .map(productEntityDtoMapper)
                .orElseThrow(() -> new RuntimeException("productEntity is null"));
    }

    @Override
    public ProductInforModel createProductInforModelByProductEntity(ProductEntity productEntity) {
        if (productEntity == null)
            throw new RuntimeException("productEntity is null");
        List<String> images = Utils.Base64Image.getImageListByPathLists(
                productEntity.getProductImageurlEntities().parallelStream().map(ProductImageurlEntity::getImageUrl)
                        .toList());
        return new ProductInforModel(productEntity, images);
    }

    @Override
    public ProductInforModel changeProductEntityToProductInforModel(ProductEntity productEntity) {
        // lấy list image url entity theo product id
        Set<ProductImageurlEntity> productImageurlEntities = productEntity.getProductImageurlEntities();
        // tạo list ảnh dạng base64
        List<String> images = Utils.Base64Image.getImageListByPathLists(productImageurlEntities.stream()
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
