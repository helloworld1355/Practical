package com.example.demo.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Myconfiguration implements WebMvcConfigurer {
    @Value("${image-path}")
    private String imagePath;

    @Value("${audio-path}")
    private String audioPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor()
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:"+imagePath);
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:"+audioPath);
    }

//    @Bean
//    public MybatisPlusInterceptor paginationInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        // 添加分页插件
//        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
//        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求。默认false
//        pageInterceptor.setOverflow(false);
//        // 单页分页条数限制，默认无限制
//        pageInterceptor.setMaxLimit(500L);
//        // 设置数据库类型
//        pageInterceptor.setDbType(DbType.MYSQL);
//        interceptor.addInnerInterceptor(pageInterceptor);
//        return interceptor;
//    }

}
