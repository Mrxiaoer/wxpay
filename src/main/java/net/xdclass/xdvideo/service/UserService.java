package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.domain.UserEntity;
import org.apache.catalina.User;

/**
 * user业务接口类
 */
public interface UserService {

    UserEntity queryOne(UserEntity userEntity);

    int insertOne(UserEntity userEntity);

    int updateOne(UserEntity userEntity);

    /**
     * 根据微信平台返回的code生成用户的信息
     * @param code
     * @return
     */
    UserEntity saveWeChatUser(String code);

}
