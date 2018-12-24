package net.xdclass.xdvideo;

import io.jsonwebtoken.Claims;
import net.xdclass.xdvideo.domain.UserEntity;
import net.xdclass.xdvideo.utils.JwtUtils;
import org.junit.Test;

/**
 * 公共类测试
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-16
 */
public class CommonTest {

    @Test
    public void testGenenJWT(){
        UserEntity user = new UserEntity();
        user.setId(111);
        user.setHeadImg("www.baidu.com");
        user.setName("xiaoxuan");

        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public void testCheck(){

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjExMSwibmFtZSI6InhpYW94dWFuIiwiaW1nIjoid3d3LmJhaWR1LmNvbSIsImlhdCI6MTUzOTY1MjgyNSwiZXhwIjoxNTM5NjU2NDI1fQ.C5SRRJ78hlz_Wmeg573XCHm84u4AWf5YieB8-rviqt0";
        Claims claims = JwtUtils.checkJWT(token);
        if (claims != null){
            String name = String.valueOf(claims.get("name"));
            int id = (Integer) claims.get("id");
            String img = (String) claims.get("img");

            System.out.println(name);
            System.out.println(id);
            System.out.println(img);

        }else {
            System.out.println("非法token");
        }

    }

}
