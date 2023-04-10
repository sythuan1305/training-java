package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.repository.ProductRepository;
import com.beetech.trainningJava.service.IFileService;
import com.beetech.trainningJava.service.IProductImageUrlService;
import com.beetech.trainningJava.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductImageUrlService productImageUrlService;

    @Autowired
    private IFileService fileService;

    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public PageModel<ProductEntity> findAll(Integer page, Integer size, String sort) {
        PageRequest pageRequest =  PageRequest.of(page < 0 ? 0 : page, size, Sort.by(sort));
        Page<ProductEntity> paging = productRepository.findAll(pageRequest);

        return new PageModel<>(paging.getContent(), pageRequest.getPageNumber(), paging.getTotalPages());
    }

    @Override
    public PageModel<ProductInforModel> findAllModel(Integer page, Integer size, String sort) {
        PageRequest pageRequest =  PageRequest.of(page < 0 ? 0 : page, size, Sort.by(sort));
        Page<ProductEntity> paging = productRepository.findAll(pageRequest);
        List<ProductInforModel> productInforModels = new ArrayList<>();
        for (ProductEntity productEntity : paging.getContent()) {
            List<ProductImageurlEntity> productImageurlEntities = productImageUrlService.findByProductId(productEntity.getId());
            List<String> images = new ArrayList<>();
            try {
                images = fileService.getImages(productImageurlEntities.stream().map(ProductImageurlEntity::getImageUrl).toList());
            } catch (Exception e) {
                e.printStackTrace();
            }
            productInforModels.add(new ProductInforModel(productEntity, images));
        }
        return new PageModel<>(productInforModels, pageRequest.getPageNumber(), paging.getTotalPages());
    }

    @Override
    public ProductEntity getOne(Integer productId) {
        if (productId == null)
            throw new RuntimeException("productId is null");
        return productRepository.getReferenceById(productId);
    }

    @Override
    public ProductInforModel getProductInforModel(Integer id) {
        List<ProductImageurlEntity> productImageurlEntities = productImageUrlService.findByProductId(id);
        List<String> images = new ArrayList<>();
        try {
            images = fileService.getImages(productImageurlEntities.stream().map(ProductImageurlEntity::getImageUrl).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ProductInforModel(getOne(id), images);
    }

    @Override
    public List<ProductEntity> getProductListByDiscountId(Integer discountId) {
        return null;
    }
}
