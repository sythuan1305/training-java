package com.beetech.trainningJava.controller.mvc.admin;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.IProductImageUrlService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.service.IFileService;
import jakarta.annotation.Nullable;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Controller("mvcAdminProductController")
@RequestMapping("/admin/product")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IProductImageUrlService productImageurlService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/upload")
    public ModelAndView uploadProduct() {
        System.out.println("upload");
        return new ModelAndView("product/upload");
    }

    @PostMapping("/upload")
    public ModelAndView uploadProduct(@RequestParam("name") String name, @RequestParam("price") BigDecimal price,
                                      @RequestParam("quantity") Integer quantity,
                                      @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {
        ModelAndView modelAndView = new ModelAndView("product/uploadSuccess");
        ProductEntity productEntity = productService.save(new ProductEntity(name, price, quantity));
        Set<ProductImageurlEntity> productImageurlEntities = productImageurlService.saves(
                fileService.uploadMultipleFiles(List.of(files), productEntity.getId()),
                productEntity.getId());
        List<String> images = fileService.getImages(productImageurlEntities.stream().map(ProductImageurlEntity::getImageUrl).toList());
        modelAndView.addObject("product", new ProductInforModel(productEntity, images));
        return modelAndView;
    }

    @PostMapping("/uploadCsv")
    public ModelAndView uploadProductCsv(@RequestParam("fileCsvJson") String fileCsvJson) throws IOException, ParseException {
        ModelAndView modelAndView = new ModelAndView("product/test");
        System.out.println(fileCsvJson);
        System.out.println(new JSONParser(fileCsvJson).parse().toString());
        return modelAndView;
    }

    @GetMapping("/uploadCsv")
    public ModelAndView uploadProductCsv() {
        System.out.println("uploadCsv");
        return new ModelAndView("product/test");
    }
}
