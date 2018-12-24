package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.EpisodeEntity;

/**
 * 集的业务实现类
 */
public interface EpisodeService {

    /**
     * 自定义查看集的数据
     * @param episodeEntity
     * @return
     */
    EpisodeEntity queryOne(EpisodeEntity episodeEntity);

    /**
     * 新增集的数据
     * @param episodeEntity
     * @return
     */
    int insertOne(EpisodeEntity episodeEntity);

    /**
     * 更新集数据
     * @param episodeEntity
     * @return
     */
    int updateOne(EpisodeEntity episodeEntity);

}
