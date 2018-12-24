package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.VideoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Video数据访问层
 */
@Mapper
public interface VideoMapper {


    List<VideoEntity> queryAll();

    /**
     * 自定义查询
     * @param video
     * @return
     */
    VideoEntity queryOne(VideoEntity video);

    /**
     * 插入video信息
     * @param video
     * @return
     */
    int insertOne(VideoEntity video);

    /**
     * 更新
     * @param video
     * @return
     */
    int updateOne(VideoEntity video);
}
