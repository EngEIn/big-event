package com.back.controller;

import com.back.pojo.Article;
import com.back.pojo.PageBean;
import com.back.pojo.Result;
import com.back.service.ArticleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     * @param article
     * @return
     */
    @PostMapping
    public Result<String> add(@RequestBody @Validated(Article.Add.class) Article article) {
        articleService.add(article);
        return Result.success();
    }

    /**
     * 分页显示文章
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param state
     * @return
     */
    @GetMapping
    public Result<PageBean<Article>> list(final Integer pageNum,
                                          final Integer pageSize,
                                          @RequestParam(required = false) final Integer categoryId,
                                          @RequestParam(required = false) final String state) {
        final PageBean<Article> articleList = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(articleList);
    }

    /**
     * 查看文章内容
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result<Article> detail(@NotNull final Integer id) {
        final Article article = articleService.findById(id);
        return Result.success(article);
    }


    /**
     * 更改文章内容
     * @param article
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.update(article);
        return Result.success();
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@NotNull final Integer id) {
        articleService.delete(id);
        return Result.success();
    }
}
