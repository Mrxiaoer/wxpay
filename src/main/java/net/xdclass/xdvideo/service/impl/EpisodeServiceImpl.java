package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.domain.EpisodeEntity;
import net.xdclass.xdvideo.mapper.EpisodeMapper;
import net.xdclass.xdvideo.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 集的业务实现层
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-19
 */
@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    EpisodeMapper episodeMapper;

    @Override
    public EpisodeEntity queryOne(EpisodeEntity episodeEntity){
        return episodeMapper.queryOne(episodeEntity);
    }

    @Override
    public int insertOne(EpisodeEntity episodeEntity){
        return episodeMapper.insertOne(episodeEntity);
    }

    @Override
    public int updateOne(EpisodeEntity episodeEntity){
        return episodeMapper.updateOne(episodeEntity);
    }
}
