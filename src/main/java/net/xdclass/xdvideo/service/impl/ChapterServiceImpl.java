package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.domain.ChapterEntity;
import net.xdclass.xdvideo.mapper.ChapterMapper;
import net.xdclass.xdvideo.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 章的业务实现层
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-19
 */
@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterMapper chapterMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ChapterEntity queryOne(ChapterEntity chapterEntity){
        return chapterMapper.queryOne(chapterEntity);
    }

    @Override
    public int insertOne(ChapterEntity chapterEntity){
        return chapterMapper.insertOne(chapterEntity);
    }

    @Override
    public  int updateOne(ChapterEntity chapterEntity){
        return chapterMapper.updateOne(chapterEntity);
    }

}
