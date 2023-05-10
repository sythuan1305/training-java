package com.beetech.trainningJava.controller.mvc.admin;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.*;
import com.beetech.trainningJava.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Controller này dùng để xử lý các request liên quan đến sản phẩm với quyền admin
 */
@Controller("mvcAdminProductController")
@RequestMapping("/admin/product")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IProductImageUrlService productImageurlService;

    @Autowired
    private ICSVService csvService;

    /**
     * Xử lý request đến trang upload sản phẩm với phương thức GET
     * @return trang upload sản phẩm
     */
    @GetMapping("/upload")
    public ModelAndView uploadProduct() {
        return new ModelAndView("product/upload");
    }

    /**
     * Xử lý request đến trang upload sản phẩm với phương thức POST
     * @param name tên sản phẩm
     * @param price giá sản phẩm
     * @param quantity số lượng sản phẩm
     * @param files danh sách file ảnh
     * @return trang upload sản phẩm thành công
     * @throws IOException ném ra exception khi lấy file ảnh từ path
     */
    @PostMapping("/upload")
    public ModelAndView uploadProduct(@RequestParam("name") String name, @RequestParam("price") BigDecimal price,
                                      @RequestParam("quantity") Integer quantity,
                                      @RequestParam(value = "files", required = false) MultipartFile[] files) {
        // lưu thông tin sản phẩm
        ProductEntity productEntity = productService.saveProductEntity(new ProductEntity(name, price, quantity));

        // lưu ảnh sản phẩm
        Set<ProductImageurlEntity> productImageurlEntities = productImageurlService.saveEntityList(
                Utils.Base64Image.uploadMultipleImagesByProductId(List.of(files), productEntity.getId()),
                productEntity);

        // lấy danh sách ảnh sản phẩm từ path
        // với ảnh là dạng mã hóa base64
        List<String> images = Utils.Base64Image.getImageListByPathLists(productImageurlEntities.stream().map(ProductImageurlEntity::getImageUrl).toList());

        // trả về trang upload sản phẩm thành công
        ModelAndView modelAndView = new ModelAndView("product/uploadSuccess");
        modelAndView.addObject("product", new ProductInforModel(productEntity, images));
        return modelAndView;
    }

    /**
     * Xử lý request đến trang upload sản phẩm trong file csv với phương thức POST
     * @return chuyển hướng đến trang danh sách sản phẩm
     */
    @PostMapping("/uploadCsv")
    public String uploadProductCsv(@RequestParam("fileCsv") MultipartFile fileCsv) {
        csvService.csvToProductInforModelList(fileCsv);
        return "redirect:/user/product/list";
    }
}
