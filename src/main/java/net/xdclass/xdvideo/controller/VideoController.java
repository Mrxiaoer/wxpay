package net.xdclass.xdvideo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.xdclass.xdvideo.domain.VideoEntity;
import net.xdclass.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    /**
     * 分页查询
     * @param page 表示当前页
     * @param size 表示每一页的个数
     * @return
     */
    @GetMapping("/page")
    public Object page(@RequestParam(value = "page",defaultValue = "1") Integer page,
                            @RequestParam(value = "size",defaultValue = "10") Integer size){

        PageHelper.startPage(page,size);
        List<VideoEntity> list = videoService.queryAll();
        PageInfo<VideoEntity> pageInfo = new PageInfo<>(list);
        Map<String,Object> data = new HashMap<>();
        data.put("total_size", pageInfo.getTotal());
        data.put("total_page", pageInfo.getPages());
        data.put("current_page", page);
        data.put("data", pageInfo.getList());

        return data;
    }

    @GetMapping("/test_page")
    public Object customPage(@RequestParam Map<String,Object> map){
        Map<String,Object> t = videoService.customPage(map);
        return t;
    }

    /**
     * 保存
     * @param video
     * @return
     */
    @PostMapping("/save")
    public int insert(@RequestBody VideoEntity video){
        videoService.insertOne(video);
        return 0;
    }

    /**
     * 测试用例
     * @param id
     * @return
     */
    @GetMapping(value = "/test")
    public VideoEntity test(@RequestParam("ids") Integer id){
        VideoEntity video = videoService.queryOne(id);
        return video;
    }

    /**
     * 更新
     * @param video
     * @return
     */
    @PostMapping("/update")
    public Object update(@RequestBody VideoEntity video){
        return videoService.updateOne(video);
    }
	
}
