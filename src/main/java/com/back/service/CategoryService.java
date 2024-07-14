package com.back.service;

import com.back.pojo.Category;

import java.util.List;

public interface CategoryService {

    //添加文章类型
    void add(final Category category);

    //文章类型列表
    List<Category> list();

    //根据id查询类型
    Category findById(final Integer id);

    //更改类型信息
    void update(final Category category);

    //删除类型信息
    void delete(final Integer id);
}
