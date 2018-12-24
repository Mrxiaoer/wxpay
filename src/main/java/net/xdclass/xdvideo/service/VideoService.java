package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.VideoEntity;

import java.util.List;
import java.util.Map;

/**
 * video的业务实现类
 */
public interface VideoService {

    /**
     * 自分页尝试
     * @param params
     * @return
     */
    Map<String,Object> customPage(Map<String,Object> params);

    /**
     * 全查
     * @return
     */
    List<VideoEntity> queryAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    VideoEntity queryOne(Integer id);

    /**
     * 插入一条数据
     * @param video
     */
    void insertOne(VideoEntity video);

    /**
     * 更新video信息
     * @param video
     * @return
     */
    int updateOne(VideoEntity video);
}
