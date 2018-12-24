package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.UserEntity;
import net.xdclass.xdvideo.domain.VideoEntity;
import net.xdclass.xdvideo.domain.VideoOrderEntity;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.mapper.UserMapper;
import net.xdclass.xdvideo.mapper.VideoMapper;
import net.xdclass.xdvideo.mapper.VideoOrderMapper;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.CommonUtils;
import net.xdclass.xdvideo.utils.HttpUtils;
import net.xdclass.xdvideo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 订单接口实现类
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018/12/1 0001
 */
@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    WeChatConfig weChatConfig;

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    VideoOrderMapper videoOrderMapper;

    @Override
    public String save(VideoOrderDto videoOrderDto) throws Exception {

        // 查找视频信息
        VideoEntity v = new VideoEntity();
        v.setId(videoOrderDto.getVideoId());
        VideoEntity vq = videoMapper.queryOne(v);

        // 查看用户信息
        UserEntity u = new UserEntity();
        u.setId(videoOrderDto.getUserId());
        UserEntity uq = userMapper.queryOne(u);

        // 生成订单
        VideoOrderEntity videoOrderEntity = new VideoOrderEntity();
        videoOrderEntity.setTotalFee(vq.getPrice());
        videoOrderEntity.setVideoImg(vq.getCoverImg());
        videoOrderEntity.setVideoTitle(vq.getTitle());
        videoOrderEntity.setCreateTime(new Date());
        videoOrderEntity.setVideoId(vq.getId());
        videoOrderEntity.setState(0);
        videoOrderEntity.setUserId(uq.getId());
        videoOrderEntity.setHeadImg(uq.getHeadImg());
        videoOrderEntity.setNickname(uq.getName());

        videoOrderEntity.setDel(0);
        videoOrderEntity.setIp(videoOrderDto.getIp());
        videoOrderEntity.setOutTradeNo(CommonUtils.generateUUID());
        videoOrderMapper.save(videoOrderEntity);

        // 获取codeUrl
        String codeUrl = unifiedOrder(videoOrderEntity);

        return codeUrl;
    }

    /**
     * 统一下单方法
     * @param videoOrderEntity
     * @return
     */
    private String unifiedOrder(VideoOrderEntity videoOrderEntity) throws Exception {

        // 生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid", weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str", CommonUtils.generateUUID());
        params.put("body",videoOrderEntity.getVideoTitle());
        params.put("out_trade_no",videoOrderEntity.getOutTradeNo());
        params.put("total_fee", videoOrderEntity.getTotalFee().toString());
        params.put("spbill_create_ip", videoOrderEntity.getIp());
        params.put("notify_url", weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");

        // sign签名
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign", sign);

//        Boolean flag =  WXPayUtil.isCorrectSign(params,weChatConfig.getKey());

        // map 转xml
        String payXml = WXPayUtil.mapToXml(params);
        System.out.println(payXml);

        // 统一下单
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(), payXml, 4000);
        if (null == orderStr){
            return null;
        }
        Map<String,String> unifiedOrderMap =  WXPayUtil.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
        // 获取codeUrl
        if (unifiedOrderMap != null){
            return unifiedOrderMap.get("code_url");
        }
        return null;
    }


    @Override
    public VideoOrderEntity findByOutTradeNo(String outTradeNo) {
        VideoOrderEntity videoOrderEntity = new VideoOrderEntity();
        videoOrderEntity.setOutTradeNo(outTradeNo);
        return videoOrderMapper.queryOne(videoOrderEntity);
    }

    @Override
    public int updateViderOrderByOutTradeNo(VideoOrderEntity videoOrderEntity) {

        return videoOrderMapper.updateViderOrderByOutTradeNo(videoOrderEntity);
    }
}
