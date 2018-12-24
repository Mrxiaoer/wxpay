package net.xdclass.xdvideo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.xdclass.xdvideo.dto.VideoOrderDto;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.IpUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018/11/27 0027
 */
@RestController
@RequestMapping("/user/api/v1/order")
//@RequestMapping("/api/v1/order")
public class VideoOrderController {

    @Autowired
    VideoOrderService videoOrderService;

    @GetMapping("add")
    public void saveOrder(@RequestParam(value = "video_id",required = true)int videoId,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ip = IpUtils.getIpAddr(request);
//        int userId = (Integer) request.getAttribute("user_id");
        int userId = 3;
//        String ip = "39.171.12.6";
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);
        String codeUrl = videoOrderService.save(videoOrderDto);
        if (codeUrl == null){
            throw new NullPointerException();
        }
        try {
            // 生成二维码
            Map<EncodeHintType,Object> hintx = new HashMap<>();
            // 设置纠错等级
            hintx.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            // 编码类型
            hintx.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE, 400,400, hintx);
            OutputStream out = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
//            response.addHeader("Access-Control-Allow-Origin", "*");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
