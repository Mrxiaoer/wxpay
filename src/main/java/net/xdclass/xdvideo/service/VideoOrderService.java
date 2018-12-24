package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.VideoOrderEntity;
import net.xdclass.xdvideo.dto.VideoOrderDto;

/**
 * 订单接口的业务接口层
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018/12/1 0001
 */
public interface VideoOrderService {

    /**
     * 保存订单
     * @param videoOrderDto
     * @return
     * @throws Exception
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    /**
     * 根据流水号查询订单
     * @param outTradeNo
     * @return
     */
    VideoOrderEntity findByOutTradeNo(String outTradeNo);

    /**
     * 根据流水号更新订单
     * @param videoOrderEntity
     * @return
     */
    int updateViderOrderByOutTradeNo(VideoOrderEntity videoOrderEntity);

}
