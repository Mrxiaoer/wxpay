package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.domain.UserEntity;
import net.xdclass.xdvideo.mapper.UserMapper;
import net.xdclass.xdvideo.service.UserService;
import net.xdclass.xdvideo.utils.HttpUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * user业务实现
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    WeChatConfig weChatConfig;

    @Override
    public UserEntity queryOne(UserEntity userEntity){
        return  userMapper.queryOne(userEntity);
    }

    @Override
    public int insertOne(UserEntity userEntity){
        return userMapper.insertOne(userEntity);
    }

    @Override
    public int updateOne(UserEntity userEntity){
        return  userMapper.updateOne(userEntity);
    }

    @Override
    public UserEntity saveWeChatUser(String code) {

        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),weChatConfig.getOpenAppid(),weChatConfig.getOpenAppsecret(),code);
        // 获取access_token
        Map<String,Object> baseMap =  HttpUtils.doGet(accessTokenUrl);

        if (baseMap == null || baseMap.isEmpty()){ return null; }
        String accessToken = (String)baseMap.get("access_token");
        String openId = (String)baseMap.get("openid");

        UserEntity dbUser = userMapper.queryByOpenId(openId);
        if(dbUser != null) {
            return dbUser;
        }
        // 获取用户基本信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openId);

        // 获取access_token
        Map<String,Object> baseUserMap =  HttpUtils.doGet(userInfoUrl);

        if (baseUserMap == null || baseUserMap.isEmpty()){ return null; }
        String nickname = (String)baseUserMap.get("nickname");
        Double sexTemp = (Double) baseUserMap.get("sex");
        int sex = sexTemp.intValue();
        String province = (String)baseUserMap.get("province");
        String headImg = (String)baseUserMap.get("headimgurl");
        String city = (String)baseUserMap.get("city");
        String country = (String)baseUserMap.get("country");

        StringBuilder address = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress =  address.toString();

        try {
            // 解决乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setOpenid(openId);
        userEntity.setName(nickname);
        userEntity.setSex(sex);
        userEntity.setHeadImg(headImg);
        userEntity.setCreateTime(new Date());
        userEntity.setCity(finalAddress);

        userMapper.insertOne(userEntity);
        return userEntity;
    }
}
