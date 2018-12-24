package net.xdclass.xdvideo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.xdclass.xdvideo.domain.VideoEntity;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<String,Object> customPage(Map<String,Object> params){
        Integer page;
        Integer size;
        if (params.containsKey("page")){
            page = Integer.parseInt(String.valueOf(params.get("page")));
        }else{
            page = 1;
        }
        if (params.containsKey("size")){
            size = Integer.parseInt(String.valueOf(params.get("szie")));
        }else{
            size = 10;
        }
        PageHelper.startPage(page, size);
        List<VideoEntity> list = this.queryAll();
        PageInfo<VideoEntity> pageInfo = new PageInfo<>(list);
        Map<String,Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("current_page", page);
        map.put("data", pageInfo.getList());
        return map;
    }

    @Override
    public List<VideoEntity> queryAll(){
        return videoMapper.queryAll();
    }


    @Override
    public VideoEntity queryOne(Integer id){
        logger.info(String.valueOf(id));
        VideoEntity v = new VideoEntity();
        v.setId(id);
        VideoEntity video = videoMapper.queryOne(v);
        return  video;
    }

    @Override
    public int updateOne(VideoEntity video){
        return videoMapper.updateOne(video);
    }

    @Override
    public void  insertOne(VideoEntity video){
        video.setCreateTime(new Date());
        videoMapper.insertOne(video);
    }
}
