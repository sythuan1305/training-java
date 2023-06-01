package com.beetech.trainningJava.controller.api.user;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.CategoryEntityDto;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.ICategoryService;
import com.beetech.trainningJava.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController("apiUserProductController")
@RequestMapping("/api/user/product")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {
    private final IProductService productService;

    private final ICategoryService categoryService;

    /**
     * Xử lý request đến trang danh sách sản phẩm
     *
     * @param pageable tham số dùng để phân trang
     * @return danh sách sản phẩm
     */
    @PostMapping("/list")
    public ResponseEntity<Object> listProduct(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        List<CategoryEntity> categoryList = categoryService.findAll();
        List<CategoryEntityDto> categoryDtoList = productService.findAllPageModelProductEntityDtoByAllCategoryAndPageable(
                categoryList, pageable);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "Success", categoryDtoList));
    }

    /**
     * Xử lý request đến trang chi tiết sản phẩm
     *
     * @param id id sản phẩm
     * @return chi tiết sản phẩm
     */
    @PostMapping("/information")
    public ResponseEntity<Object> GetInformation(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "Success", productService.getProductEntityDtoById(id)));
    }
}
