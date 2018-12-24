package net.xdclass.xdvideo.mapper;

import net.xdclass.xdvideo.domain.VideoOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单dao层
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018/11/29 0029
 */
@Mapper
public interface VideoOrderMapper {

    /**
     * 保存
     * @param videoOrderEntity
     * @return
     */
    int save(VideoOrderEntity videoOrderEntity);

    /**
     * 自定义查询one
     * @param videoOrderEntity
     * @return
     */
    VideoOrderEntity queryOne(VideoOrderEntity videoOrderEntity);

    /**
     * 自定义查询list outTradeNo
     * @param videoOrderEntity
     * @return
     */
    List<VideoOrderEntity> queryList(VideoOrderEntity videoOrderEntity);

    /**
     * 更新数据
     * @param videoOrderEntity
     */
    int updateBit(VideoOrderEntity videoOrderEntity);

    /**
     * 根据流水号更新信息
     * @param videoOrderEntity
     * @return
     */
    int updateViderOrderByOutTradeNo(VideoOrderEntity videoOrderEntity);

    /**
     * 删除订单
     * @param id
     * @param userId
     * @return
     */
    int del(@Param("id") int id,@Param("userId") int userId);


}
