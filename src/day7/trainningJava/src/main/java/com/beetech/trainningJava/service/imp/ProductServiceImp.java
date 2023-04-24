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

/**
 * Class này dùng để implement interface IProductService
 * @see IProductService
 */
@Service
public class ProductServiceImp implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductImageUrlService productImageUrlService;

    @Autowired
    private IFileService fileService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductEntity saveProductEntity(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }


    @Override
    public PageModel<ProductEntity> findPageModeProductEntitylByPageIndex(Integer pageIndex, Integer size, String sort) {
        // lấy page product entity theo page index, size, sort
        PageRequest pageRequest = PageRequest.of(pageIndex < 0 ? 0 : pageIndex, size, Sort.by(sort));
        Page<ProductEntity> paging = productRepository.findAll(pageRequest);

        // trả về page model product entity
        return new PageModel<>(paging.getContent(), pageRequest.getPageNumber(), paging.getTotalPages());
    }

    @Override
    public PageModel<ProductInforModel> findPageModelProductInforModelByPageIndex(Integer pageIndex, Integer size, String sort) {
        // lấy page product entity theo page index, size, sort
        PageRequest pageRequest =  PageRequest.of(pageIndex < 0 ? 0 : pageIndex, size, Sort.by(sort));
        Page<ProductEntity> paging = productRepository.findAll(pageRequest);

        // tạo list product infor model
        List<ProductInforModel> productInforModels = new ArrayList<>();
        for (ProductEntity productEntity : paging.getContent()) {
            // tạo product infor model từ product entity
            ProductInforModel productInforModel = getProductInforModelById(productEntity.getId());
            // thêm product infor model vào list product infor model
            productInforModels.add(productInforModel);
        }

        // trả về page model product infor model
        return new PageModel<>(productInforModels, pageRequest.getPageNumber(), paging.getTotalPages());
    }

    @Override
    public ProductEntity getProductEntityById(Integer productId) {
        if (productId == null)
            throw new RuntimeException("productId is null");
        return productRepository.getReferenceById(productId);
    }

    @Override
    public ProductInforModel getProductInforModelById(Integer id) {
        // lấy list image url entity theo product id
        List<ProductImageurlEntity> productImageurlEntities = productImageUrlService.findEntityByProductId(id);
        List<String> images = new ArrayList<>();
        try {
            // tạo ảnh dạng base64 từ danh sách path
            List<String> pathList = productImageurlEntities.stream().map(ProductImageurlEntity::getImageUrl).toList();
            images = fileService.getImageListByPathLists(pathList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // trả về product infor model
        return new ProductInforModel(getProductEntityById(id), images);
    }

    @Override
    public ProductEntity findProductEntityByProductName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void TestMinusQuantity(Integer number) {
        ProductEntity productEntity = productRepository.getReferenceById(1);
        productEntity.setQuantity(productEntity.getQuantity() - number);
    }
}
