package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.JsonData;
import net.xdclass.xdvideo.domain.UserEntity;
import net.xdclass.xdvideo.domain.VideoOrderEntity;
import net.xdclass.xdvideo.service.UserService;
import net.xdclass.xdvideo.service.VideoOrderService;
import net.xdclass.xdvideo.utils.JwtUtils;
import net.xdclass.xdvideo.utils.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

/**
 * 微信扫码登录相关
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-16
 */
@RestController
@RequestMapping("/api/v1/wechat")
public class WechatController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    UserService userService;

    @Autowired
    VideoOrderService videoOrderService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("login_url")
    public JsonData loginUrl(@RequestParam(value = "access_page",required = true)String accessPage) throws UnsupportedEncodingException {

        // 获取开发平台重定向地址
        String redirectUrl = weChatConfig.getOpenRedirectUrl();

        //进行编码
        String callbackUrl = URLEncoder.encode(redirectUrl, "GBK");

        String qrcodeUrl = String.format(WeChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppid(),callbackUrl,accessPage);

        return  JsonData.buildSuccess(qrcodeUrl);
    }


    /**
     * 微信扫码登录回调地址
     * @param code
     * @param state
     * @param response
     * @throws IOException
     */
    @GetMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code",required = true) String code,
                                   String state, HttpServletResponse response) throws IOException {

        UserEntity userEntity = userService.saveWeChatUser(code);
        // 生成jwt
        if (userEntity != null){
            String token = JwtUtils.geneJsonWebToken(userEntity);
            // state 当前用户的页面地址,需要拼接http：// 这样才不会站内跳转
            response.sendRedirect(state + "?token=" + token +"&head_img=" + userEntity.getHeadImg() + "&name=" + URLEncoder.encode(userEntity.getName(),"UTF-8"));

        }
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     */
    @RequestMapping("/order/callback")
    public void orderCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{

        InputStream inputStream = request.getInputStream();
        // BufferedReader是包装设计模式，性能比较高
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap.toString());

        SortedMap<String,String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

        // 判断签名是否正确
        if (WXPayUtil.isCorrectSign(sortedMap,weChatConfig.getKey())){
            if ("SUCCESS".equals(sortedMap.get("return_code"))){
                // 根据流水号查询信息
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrderEntity dbvideoOrderEntity = videoOrderService.findByOutTradeNo(outTradeNo);
                // 更新订单状态
                if ( dbvideoOrderEntity!= null && dbvideoOrderEntity.getState() == 0 ){
                    VideoOrderEntity videoOrderEntity = new VideoOrderEntity();
                    videoOrderEntity.setOpenid(sortedMap.get("appid"));
                    videoOrderEntity.setOutTradeNo(sortedMap.get("out_trade_no"));
                    videoOrderEntity.setNotifyTime(new Date());
                    videoOrderEntity.setState(1);
                    int rows = videoOrderService.updateViderOrderByOutTradeNo(videoOrderEntity);
                    // 影响行数row == 1 或者 row == 0 响应微信成功或者失败
                    if ( rows == 1 ){
                        // 通知微信订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().println("success");
                        return;
                    }
                }
            }
        }
        // 其他情况都处理失败
        response.setContentType("text/xml");
        response.getWriter().println("fail");
    }



}
