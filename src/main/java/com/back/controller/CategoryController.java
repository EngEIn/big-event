package com.back.controller;

import com.back.pojo.Category;
import com.back.pojo.Result;
import com.back.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加文章类别
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    /**
     * 显示所有的文章类别
     * @return
     */
    @GetMapping
    public Result<List<Category>> list() {
        final List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    /**
     * 查看文章类型信息
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result<Category> detail(@NotNull final Integer id) {
        final Category category = categoryService.findById(id);
        return Result.success(category);
    }

    /**
     * 更改文章类型信息
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    /**
     * 删除文章类型
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@NotNull final Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
}
