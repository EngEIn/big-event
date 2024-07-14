package com.back.service;

import com.back.pojo.Article;
import com.back.pojo.PageBean;

public interface ArticleService {

    //添加文章
    void add(final Article article);

    PageBean<Article> list(final Integer pageNum,
                           final Integer pageSize,
                           final Integer categoryId,
                           final String state);

    //根据id查找文章
    Article findById(final Integer id);

    //更改文章
    void update(final Article article);

    //删除文章
    void delete(Integer id);
}
