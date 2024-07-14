package com.back.mapper;

import com.back.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    /**
     * 添加文章
     * @param article
     */
    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())")
    void add(final Article article);

    /**
     * 文章列表
     * @param id
     * @param categoryId
     * @param state
     * @return
     */
    List<Article> list(final Integer id, final Integer categoryId, final String state);

    /**
     * 根据文章id查询文章
     * @param id
     * @return
     */
    @Select("select * from article where id=#{id}")
    Article findById(final Integer id);

    /**
     * 更改文章信息
     * @param article
     */
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=now() where id=#{id}")
    void update(final Article article);

    /**
     * 删除文章
     * @param id
     */
    @Delete("delete from article where id=#{id}")
    void delete(final Integer id);
}
