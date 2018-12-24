package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.ChapterEntity;

/**
 * 章的业务实现类
 */
public interface ChapterService {

    /**
     * 自定义查询章的信息
     * @param chapterEntity
     * @return
     */
    ChapterEntity queryOne(ChapterEntity chapterEntity);


    /**
     * 新增章的数据
     * @param chapterEntity
     * @return
     */
    int insertOne(ChapterEntity chapterEntity);

    /**
     * 更新章的数据
     * @param chapterEntity
     * @return
     */
    int updateOne(ChapterEntity chapterEntity);

}
