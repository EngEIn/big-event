package com.back.mapper;

import com.back.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 添加文章类型
     * @param category
     */
    @Insert("insert into category(category_name, category_alias,create_user, create_time, update_time) " +
            "values(#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(final Category category);

    /**
     * 文章类别列表
     * @param id
     * @return
     */
    @Select("select * from category where create_user=#{id}")
    List<Category> list(final Integer id);

    /**
     * 根据文章类别id查询文章
     * @param id
     * @return
     */
    @Select("select * from category where id=#{id}")
    Category findById(final Integer id);

    /**
     * 更改文章类别信息
     * @param category
     */
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id=#{id}")
    void update(final Category category);

    /**
     * 删除文章类别
     * @param id
     */
    @Delete("delete from category where id=#{id}")
    void delete(final Integer id);
}
