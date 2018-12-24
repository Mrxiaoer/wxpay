package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.ChapterEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 章数据访问层
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-16
 */
@Mapper
public interface ChapterMapper {

    /**
     * 自定义查看章信息
     * @param chapterEntity
     * @return
     */
    ChapterEntity queryOne(ChapterEntity chapterEntity);

    /**
     * 新增章信息
     * @param chapterEntity
     * @return
     */
    int insertOne(ChapterEntity chapterEntity);

    /**
     * 更新章信息
     * @param chapterEntity
     * @return
     */
    int updateOne(ChapterEntity chapterEntity);
}
