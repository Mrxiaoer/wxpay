package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.EpisodeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 集数据访问层
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-16
 */
@Mapper
public interface EpisodeMapper {

    /**
     * 自定义查看一条
     * @param episodeEntity
     * @return
     */
    EpisodeEntity queryOne(EpisodeEntity episodeEntity);

    /**
     * 新增集数据
     * @param episodeEntity
     * @return
     */
    int insertOne(EpisodeEntity episodeEntity);

    /**
     * 更新集信息
     * @param episodeEntity
     * @return
     */
    int updateOne(EpisodeEntity episodeEntity);
}
