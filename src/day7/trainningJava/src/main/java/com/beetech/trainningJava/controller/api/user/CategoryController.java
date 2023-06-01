package com.beetech.trainningJava.controller.api.user;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInCategoryModel;
import com.beetech.trainningJava.model.ProductsByCategoryModel;
import com.beetech.trainningJava.service.ICategoryService;
import com.beetech.trainningJava.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("apiUserCategoryController")
@RequestMapping("/api/user/category")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryController {
    private final IProductService productService;

    private final ICategoryService categoryService;

    @PostMapping("/{categoryName}")
    public ResponseEntity<Object> listProductByCategory1(@PageableDefault(size = 5) Pageable pageable,
                                                         @PathVariable("categoryName") String categoryName) {
        return ResponseEntity.ok(
                new ApiResponse(
                        HttpStatus.OK.value(),
                        true,
                        "Success",
                        categoryService.findAllProductByCategoryName(categoryName, pageable)));
    }
}
