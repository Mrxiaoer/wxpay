package net.xdclass.xdvideo.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mybatis分页插件配置
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-10-15
 */
@Configuration
public class MyBatisConfig {
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();

        //设置为true时，会将rowBounds第一个参数offset当成pagenum页码使用
        p.setProperty("offsetAsPageNum","true");

        //设置为true时，使用rowBounds分页会进行count查询
        p.setProperty("rowBoundsWithCount","true");
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
